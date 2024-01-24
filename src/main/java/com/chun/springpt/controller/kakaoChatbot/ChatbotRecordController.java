package com.chun.springpt.controller.kakaoChatbot;

import com.chun.springpt.service.kakaoChatbot.KakaoChatbotService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@EnableAsync
@SuppressWarnings("unchecked")
@RequestMapping(value = "/chatbot")
public class ChatbotRecordController {

    @Autowired
    private KakaoChatbotService kakaoChatbotService;

    @RequestMapping("/record_meal")
    @ResponseBody
    public Map<String, Object> recordMeal(@RequestBody String body) {

        JSONObject loadJson = new JSONObject(body);
        System.out.println("loadJson: " + loadJson);

        String plusfriendUserKey = kakaoChatbotService.getPlusfriendUserKey(body);
        String userName = kakaoChatbotService.getUserNameByPlusfriendUserKey(plusfriendUserKey);

        // 사진이 들어오지 않으면 유효시간이 지났습니다.
        // 식사시간이 none이면 유효하지 않은 요청입니다.
        // userName이 null이면 연동코드와 연결된 계정이 없습니다.

        return Map.of(
            "version", "2.0",
            "template", Map.of(
                "outputs", Map.of(
                    "simpleText", Map.of(
                        "text", "아래 내용을 기록합니다. \n\n" +
                            "날짜" + "{......}" + "\n" +
                            "식사 시간" + "{......}" + "\n\n" +

                            "음식 이름" + "{......}" + "\n" +
                            "칼로리" + "{......}" + "\n" +
                            "탄수화물" + "{......}" + "\n" +
                            "단백질" + "{......}" + "\n" +
                            "지방" + "{......}" + "\n"
                    )
                )
            )
        );
    }
}
