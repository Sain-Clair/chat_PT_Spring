package com.chun.springpt.service.kakaoChatbot;

import com.chun.springpt.dao.AuthDao;
import com.chun.springpt.dao.FoodDao;
import com.chun.springpt.vo.FoodVO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@SuppressWarnings("unchecked")
public class AnalysisPictureService {

    private final WebClient webClient;

    public final String djangoBaseUrl = "http://43.201.156.237:9000";

    @Autowired
    private FoodDao foodDao;

    public AnalysisPictureService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
            .baseUrl(djangoBaseUrl)
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)) // 16MB로 증가
            .build();
    }

    @Async
    @ResponseBody
    public void analysis_picture(String callbackUrl, List<String> secureUrlsList, String userName, String meal_time) {
        // `formData`를 생성하여 userName과 URL 리스트를 포함시킨다.
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("userName", userName);
        formData.add("meal_time", meal_time);

        // secureUrlsList에 있는 각 URL을 formData에 추가합니다.
        for (int i = 0; i < secureUrlsList.size(); i++) {
            formData.add("image" + i, secureUrlsList.get(i));
        }

        // Django로 사진 분석 요청
        Map<String, Object> responseFromDjango = webClient.post()
            .uri("/imgmodel/findFood_for_kakao")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData(formData))
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
            })
            .block();

        System.out.println("responseFromDjango: " + responseFromDjango);

        // 캐루셀 5개
        List<Map<String, Object>> carouselItems = new ArrayList<>();
        // 예측결과 리스트
        List<Map<String, Object>> results = (List<Map<String, Object>>) responseFromDjango.get("results");
        // foodnum을 통해 음식 정보를 가져오기
        List<FoodVO> foodVOList = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            foodVOList.add(foodDao.selecOnetFood(Integer.parseInt(results.get(i).get("foodnum").toString())));
            Map<String, Object> item = Map.of(
                "imageTitle", Map.of(
                    "title", foodVOList.get(i).getFOODNAME(),
                    "description", foodVOList.get(i).getFOODNAME() + "일 확률이 " + results.get(i).get("predictrate").toString() + "%입니다. \n"
                ),
                "title", "<분석 결과 후보>",
                "description",
                foodDao.selecOnetFood(Integer.parseInt(results.get(i).get("candidate1").toString())).getFOODNAME() + ", "
                    + foodDao.selecOnetFood(Integer.parseInt(results.get(i).get("candidate2").toString())).getFOODNAME() + ", "
                    + foodDao.selecOnetFood(Integer.parseInt(results.get(i).get("candidate3").toString())).getFOODNAME(),
                "thumbnail", Map.of(
                    "imageUrl", secureUrlsList.get(i),
                    "width", "800",
                    "height", "800"
                ),
                "profile", Map.of(
                    "title", meal_time + "식단 분석"
                ),
                "itemList", List.of(
                    Map.of("title", "기준", "description", foodVOList.get(i).getFOODWEIGHT()) + "(g)",
                    Map.of("title", ".", "description", "."),
                    Map.of("title", "칼로리", "description", foodVOList.get(i).getFOODCAL()) + "kcal",
                    Map.of("title", "탄수화물", "description", foodVOList.get(i).getFOOD_TAN()) + "(g)",
                    Map.of("title", "단백질", "description", foodVOList.get(i).getFOOD_DAN()) + "(g)",
                    Map.of("title", "지방", "description", foodVOList.get(i).getFOOD_GI()) + "(g)"
                ),
                "buttons", List.of(
                    Map.of(
                        "action", "message",
                        "label", "이대로 기록",
                        "messageText", "식단 기록하기",
                        "extra", Map.of(
                            "userName", userName,
                            "식사시간", meal_time,
                            "음식식별번호", results.get(i).get("foodnum").toString(),
                            "음식명", foodVOList.get(i).getFOODNAME(),
                            "기준", foodVOList.get(i).getFOODWEIGHT() ,
                            "칼로리", foodVOList.get(i).getFOODCAL(),
                            "탄수화물", foodVOList.get(i).getFOOD_TAN(),
                            "단백질", foodVOList.get(i).getFOOD_DAN(),
                            "지방", foodVOList.get(i).getFOOD_GI()
                        )
                    ),
                    Map.of(
                        "action", "message",
                        "label", "다른 후보로 기록",
                        "messageText", "다른 후보로 기록",
                        "extra", Map.of(
                            "userName", userName,
                            "식사시간", meal_time,
                            "1", results.get(i).get("candidate1").toString(),
                            "2", results.get(i).get("candidate2").toString(),
                            "3", results.get(i).get("candidate3").toString()
                        )
                    )
                ),
                "itemListAlignment", "right",
                "buttonLayout", "vertical"
            );
            carouselItems.add(item);
        }

        Map<String, Object> result = Map.of(
            "version", "2.0",
            "useCallback", "True",
            "template", Map.of(
                "outputs", List.of(
                    Map.of(
                        "simpleText", Map.of(
                            "text", "분석결과를 알려드릴게요."
                        )
                    ),
                    Map.of(
                        "carousel", Map.of(
                            "type", "itemCard",
                            // 캐루셀 5개
                            "items", carouselItems
                        )
                    )
                )
            )
        );

        System.out.println("result: " + result);

        // HttpClient 인스턴스 생성
        HttpClient client = HttpClient.newHttpClient();
        System.out.println("HttpClient 인스턴스 생성");

        // HttpRequest 인스턴스 생성
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(callbackUrl))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(new JSONObject(result).toString()))
            .build();
        System.out.println("HttpRequest 인스턴스 생성");

        try {
            // 요청 보내기 및 응답 받기
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("카카오로부터 온 응답 : " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
