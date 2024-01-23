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
import java.util.Map;

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

    @RequestMapping("/record_meal")
    @ResponseBody
    public Map<String, Object> recordMeal(@RequestBody String body) {

        String callbackUrl = kakaoChatbotService.getCallbackUrl(body);

        String plusfriendUserKey = kakaoChatbotService.getPlusfriendUserKey(body);
        String userName = kakaoChatbotService.getUserNameByPlusfriendUserKey(plusfriendUserKey);

        // 분석 결과를 보내는 메소드
        analysisPictureService.analysis_picture(callbackUrl);

        // 일단 요청에 대한 응답을 보냄
        return Map.of(
            "version", "2.0",
            "useCallback", "True",
            "data", Map.of(
                "msg", "사진을 분석하는 동안 잠시 기다려 주세요!"
            )
        );
    }
}
