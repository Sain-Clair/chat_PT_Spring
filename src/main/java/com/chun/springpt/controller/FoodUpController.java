package com.chun.springpt.controller;

import com.chun.springpt.service.FoodUpService;
import com.chun.springpt.utils.JwtUtil;
import com.chun.springpt.vo.FoodUpVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Slf4j
public class FoodUpController {
  private final WebClient webClient;
  @Autowired
  private FoodUpService foodUpService;

  public FoodUpController(WebClient.Builder webClientBuilder,
                          @Value("${django.base.url}") String djangoBaseUrl) {
    this.webClient = webClientBuilder
            .baseUrl(djangoBaseUrl)
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

    Date date = null;

    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    for (Map.Entry<String, String> entry : fileMap.entrySet()) {
      String key = entry.getKey(); // 키 추출
      if ("date".equals(key)){
        String dateStr = entry.getValue();
        if (dateStr != null) {
          SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
          try {
            date = dateFormat.parse(dateStr);
          } catch (ParseException e) {
            log.error("날짜 파싱 오류", e);
            // 적절한 예외 처리 또는 반환
          }
        }
      }else {
        log.info("Processing key: " + key); // 추출된 키 로깅
        String base64String = entry.getValue();
        if (base64String != null && !base64String.isEmpty()) {
          formData.add(key, base64String);
        }
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
        foodUpService.transactionInsertUpdate(vo);

        //이미지처리
        byte[] imageBytes = Base64.getDecoder().decode((String)result.get("base64_encoded_data"));
        String filePath = "/src/main/resources/static/images/upphoto/" + upphotoid + ".jpg";
        try (FileOutputStream imageOutFile = new FileOutputStream(filePath)) {
          imageOutFile.write(imageBytes);
          log.info("Image written to file successfully"); // 이미지 파일 저장 성공 로깅
        } catch (IOException e) {
          log.error("Error saving image file: " + e.getMessage()); // 이미지 파일 저장 실패 로깅
        }
      }
    }
  }
}
