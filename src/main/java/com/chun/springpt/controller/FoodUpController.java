package com.chun.springpt.controller;

import com.chun.springpt.service.FoodUpService;
import com.chun.springpt.utils.JwtUtil;
import com.chun.springpt.vo.FoodUpVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Slf4j
public class FoodUpController {
  private final WebClient webClient;
  @Autowired
  private FoodUpService foodUpService;

  public FoodUpController(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder
            .baseUrl("http://localhost:9000")
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)) // 16MB로 증가
            .build();
  }

  @PostMapping("/food_up")
  public void food_upload(HttpServletRequest request, @RequestParam Map<String, String> fileMap) throws Exception {
    String authorizationHeader = request.getHeader("Authorization");
    String token = JwtUtil.extractToken(authorizationHeader);
    String userName = JwtUtil.getID(token);

    Calendar calendar = Calendar.getInstance();
    calendar.set(2024, Calendar.JANUARY, 19, 0, 0, 0);
    calendar.set(Calendar.MILLISECOND, 0);

    Date date = calendar.getTime();

    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    for (Map.Entry<String, String> entry : fileMap.entrySet()) {
      String key = entry.getKey(); // 키 추출
      log.info("Processing key: " + key); // 추출된 키 로깅
      String base64String = entry.getValue();
      if (base64String != null && !base64String.isEmpty()) {
        formData.add(key, base64String);
      }
    }
    formData.add("userName", userName);

    Map<String, Object> responseFromDjango = webClient.post()
            .uri("/imgmodel/findFood")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData(formData))
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
            .block();

    List<Map<String, Object>> results = (List<Map<String, Object>>) responseFromDjango.get("results");
    if (results != null) {
      for (Map<String, Object> result : results) {
        int upphotoid = foodUpService.getNextUpPhotoId();
        log.info("upphotoid : " + upphotoid);
        FoodUpVO vo = new FoodUpVO();
        vo.setUpphotoid(upphotoid);
        vo.setNormalId(userName);
        vo.setCategory((String) result.get("category"));
        vo.setFoodnum((Integer) result.get("foodnum"));
        vo.setCandidate1((Integer) result.get("candidate1"));
        vo.setCandidate2((Integer) result.get("candidate2"));
        vo.setCandidate3((Integer) result.get("candidate3"));
        vo.setPredictrate((Double) result.get("predictrate"));
        vo.setCandidate1rate((Double) result.get("candidate1rate"));
        vo.setCandidate2rate((Double) result.get("candidate2rate"));
        vo.setCandidate3rate((Double) result.get("candidate3rate"));
        vo.setUploaddate(date);
        vo.setNnum(foodUpService.selectNnum(vo.getNormalId()));

        //sql처리
        transactionInsertUpdate(vo);

        //이미지처리
        byte[] imageBytes = Base64.getDecoder().decode((String)result.get("base64_encoded_data"));
        String filePath = "E:/chat_PT_Spring/src/main/resources/static/images/upphoto/" + upphotoid + ".jpg";
        try (FileOutputStream imageOutFile = new FileOutputStream(filePath)) {
          imageOutFile.write(imageBytes);
          log.info("Image written to file successfully"); // 이미지 파일 저장 성공 로깅
        } catch (IOException e) {
          log.error("Error saving image file: " + e.getMessage()); // 이미지 파일 저장 실패 로깅
        }
      }
    }
  }

  public void transactionInsertUpdate(FoodUpVO vo){
    log.info("transactionInsertUpdate 실행");
    //insert
    foodUpService.insertUpPhoto(vo);

    //select
    int nnum = vo.getNnum();
    Date inputDate = vo.getUploaddate();
    log.info("Input Date: " + inputDate);

    // 날짜 정보만 비교하기 위해 시간 부분 제거
    inputDate = removeTime(inputDate);
    log.info("Input Calendar Date: " + inputDate);

    List<Map<String,Object>> ratingList = foodUpService.selectRatingDate(nnum);

    // 입력된 날짜보다 최신인 날짜들의 개수를 계산하기 위한 변수
    int newerDatesCount = 0;

    boolean isUpdate = true;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
    Set<String> countedDates = new HashSet<>();

    // 기존 데이터와 입력된 날짜를 비교
    for (Map<String, Object> rating : ratingList) {
      Date existingDate = removeTime((Date) rating.get("UPLOADDATE"));

      // 날짜를 문자열로 변환
      String dateString = dateFormat.format(existingDate.getTime());

      // 입력된 날짜보다 최신이면서 아직 카운트되지 않은 날짜를 카운트
      if (existingDate.equals(inputDate)) {
        isUpdate = false;
        break;
      }else if (existingDate.after(inputDate) && !countedDates.contains(dateString)){
        newerDatesCount++;
        countedDates.add(dateString); // 이미 카운트된 날짜 추가
      }
    }
    // 새로운 데이터의 평점 계산
    int newRating = 10 - newerDatesCount;
    log.info("New Rating: " + newRating);

    // 새로운 데이터를 위한 맵 객체 생성 및 데이터 추가
    Map<String, Object> mfMap = new HashMap<>();
    mfMap.put("foodnum", vo.getFoodnum());
    mfMap.put("nnum", vo.getNnum());
    mfMap.put("upphotoid", vo.getUpphotoid());
    mfMap.put("rating", newRating);
    foodUpService.insertMemberFood(mfMap); // DB에 데이터 추가

    if(isUpdate) {
      //입력 날짜 이전 날짜의 데이터 모두 rating - 1 하고 5이하 삭제
      foodUpService.updateSubtractFood(inputDate);
      foodUpService.deleteMemberFood(nnum);
    }
  }
  private Date removeTime(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTime();
  }

}
