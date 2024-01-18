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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FoodUpController {
  private final WebClient webClient;
  @Autowired
  private FoodUpService foodUpService;

  public FoodUpController(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("http://localhost:9000").build();
  }

  @PostMapping("/food_up")
  public void food_upload(HttpServletRequest request, @RequestParam Map<String, String> fileMap) throws Exception {
    String authorizationHeader = request.getHeader("Authorization");
    String token = JwtUtil.extractToken(authorizationHeader);
    String userName = JwtUtil.getID(token);

    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    for (Map.Entry<String, String> entry : fileMap.entrySet()) {
      String base64String = entry.getValue();
      if (base64String != null && !base64String.isEmpty()) {
        formData.add(entry.getKey(), base64String);
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
    log.info("responseFromDjango : " + responseFromDjango);

    List<Map<String, Object>> results = (List<Map<String, Object>>) responseFromDjango.get("results");
    if (results != null) {
      for (Map<String, Object> result : results) {
        String category = (String) result.get("category");
        int upphotoid = foodUpService.getNextUpPhotoId();
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
        vo.setNnum(foodUpService.getNnumByNormalId(vo.getNormalId()));
        foodUpService.foodUpload(vo);

        foodUpService.insertMemberFood(vo);

        // 이미지 데이터 키 생성 및 처리
        for (Map.Entry<String, String> fileEntry : fileMap.entrySet()) {
          String key = fileEntry.getKey();
          if (key.startsWith(category + "[")) {
            String base64Image = fileEntry.getValue();
            if (base64Image != null && !base64Image.isEmpty()) {
              String imageDataBytes = base64Image.substring(base64Image.indexOf(",") + 1);
              byte[] imageBytes = Base64.getDecoder().decode(imageDataBytes);

              String filePath = "E:/chat_PT_Spring/src/main/resources/static/images/upphoto/" + upphotoid + ".jpg";
              try (FileOutputStream imageOutFile = new FileOutputStream(filePath)) {
                imageOutFile.write(imageBytes);
              } catch (IOException e) {
                log.error("Error saving image file: " + e.getMessage());
              }
              break; // 이미지 처리 후 해당 category에 대한 반복 종료
            }
          }
        }
      }



    }
  }

}
