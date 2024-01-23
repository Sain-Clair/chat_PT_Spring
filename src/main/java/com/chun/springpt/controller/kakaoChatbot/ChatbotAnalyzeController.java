package com.chun.springpt.controller.kakaoChatbot;

import com.chun.springpt.service.kakaoChatbot.AnalysisPictureService;
import com.chun.springpt.service.kakaoChatbot.KakaoChatbotService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@EnableAsync
@SuppressWarnings("unchecked")
@RequestMapping(value = "/chatbot")
public class ChatbotAnalyzeController {

    @Autowired
    private KakaoChatbotService kakaoChatbotService;

    @Autowired
    private AnalysisPictureService analysisPictureService;

    @RequestMapping("/analyze_meal")
    @ResponseBody
    public Map<String, Object> recordMeal(@RequestBody String body) {

        JSONObject loadJson = new JSONObject(body);

        String callbackUrl = kakaoChatbotService.getCallbackUrl(body);
        String plusfriendUserKey = kakaoChatbotService.getPlusfriendUserKey(body);
        String userName = kakaoChatbotService.getUserNameByPlusfriendUserKey(plusfriendUserKey);

        // "meal_time" 값을 추출
        String meal_time =  loadJson.getJSONObject("action").getJSONObject("clientExtra").optString("meal_time", "none");

        // 이미지 url 추출
        String secureUrlsJsonString = loadJson.getJSONObject("action").getJSONObject("detailParams").getJSONObject("secureimage").getString("value");
        JSONObject secureUrlsObject = new JSONObject(secureUrlsJsonString);
        String secureUrlsString = secureUrlsObject.getString("secureUrls");
        secureUrlsString = secureUrlsString.replace("List(", "").replace(")", ""); // "List("와 ")"를 제거하고 URL들을 분리
        List<String> secureUrlsList = Arrays.stream(secureUrlsString.split(",")).map(String::trim).collect(Collectors.toList()); // 쉼표로 구분된 URL 문자열을 리스트로 변환.

        // 사진이 5장 이상이면 더 이상 진행 안함
        if (secureUrlsList.size() > 5) {
            return Map.of(
                "version", "2.0",
                "useCallback", "True",
                "data", Map.of(
                    "msg", "한번에 최대 5장의 사진을 등록할 수 있습니다."
                )
            );
        }

        // 비회원 유저는 식단 기록 못함
        String msg = "사진을 분석하는 동안 잠시 기다려 주세요!";
        if (userName == null) {
            msg = "식단을 저장 하시려면 회원가입을 해주세요. 사진을 분석하는 동안 잠시 기다려 주세요!";
        }

        // 분석 결과를 보내는 메소드
        analysisPictureService.analysis_picture(callbackUrl, secureUrlsList, userName, meal_time);

        // 일단 요청에 대한 응답을 보냄
        return Map.of(
            "version", "2.0",
            "useCallback", "True",
            "data", Map.of(
                "msg", msg
            )
        );
    }
}
