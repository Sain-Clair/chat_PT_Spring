package com.chun.springpt.controller;

import com.chun.springpt.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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

import java.util.Base64;
import java.util.Map;

@RestController
@Slf4j
public class FoodUpController {
  private final WebClient webClient;

  public FoodUpController(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("http://localhost:9000").build();
  }

  @PostMapping("/food_up")
  public Map<String, Object> food_upload(HttpServletRequest request,
                                         @RequestParam Map<String, MultipartFile> fileMap) throws Exception {
    String authorizationHeader = request.getHeader("Authorization");
    String token = JwtUtil.extractToken(authorizationHeader);
    String userName = JwtUtil.getID(token);

    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
      MultipartFile file = entry.getValue();
      if (!file.isEmpty()) {
        byte[] bytes = file.getBytes();
        String base64String = Base64.getEncoder().encodeToString(bytes);
        formData.add(entry.getKey(), base64String);
      }
    }
    formData.add("userName", userName);

    // WebClient를 동기적으로 사용하여 결과를 받아옴
    Map<String, Object> responseFromDjango = webClient.post()
            .uri("/imgmodel/findFood")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData(formData))
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
            })
            .block(); // block()을 사용하여 Mono를 동기적으로 처리
    // 결과를 Vue로 반환
    return responseFromDjango;
  }
}
