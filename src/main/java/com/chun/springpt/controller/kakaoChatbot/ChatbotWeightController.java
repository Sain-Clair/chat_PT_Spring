package com.chun.springpt.controller.kakaoChatbot;

import com.chun.springpt.service.MemberService;
import com.chun.springpt.service.kakaoChatbot.AnalysisPictureService;
import com.chun.springpt.service.kakaoChatbot.KakaoChatbotService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@EnableAsync
@SuppressWarnings("unchecked")
@RequestMapping(value = "/chatbot")
public class ChatbotWeightController {

    @Autowired
    private KakaoChatbotService kakaoChatbotService;

    @Autowired
    private MemberService Mservice;

    @RequestMapping("/record_weight")
    @ResponseBody
    public Map<String, Object> recordWeight(@RequestBody String body) {

        JSONObject loadJson = new JSONObject(body);

        String plusfriendUserKey = kakaoChatbotService.getPlusfriendUserKey(body);
        String userName = kakaoChatbotService.getUserNameByPlusfriendUserKey(plusfriendUserKey);
        String nickname = kakaoChatbotService.getNicknameByPlusfriendUserKey(plusfriendUserKey);

        // 만약 일반회원이 아니거나, 연동코드로 일반회원의 정보를 가져오지 못했다면,
        if (nickname == null) {
            return Map.of(
                "version", "2.0",
                "template", Map.of(
                    "outputs", List.of(
                        Map.of(
                            "textCard", Map.of(
                                "title", "연동코드와 연결된 계정이 없습니다.",
                                "description", "코드를 회원가입시 또는 회원정보수정 페이지에서 입력해주세요.\n",
                                "buttons", List.of(
                                    Map.of(
                                        "action", "webLink",
                                        "label", "회원가입",
                                        "webLinkUrl", "http://www.chatpt.shop/signUp/sign_up_main"
                                    )
                                )
                            )
                        ),
                        Map.of(
                            "simpleText", Map.of(
                                "text", plusfriendUserKey
                            )
                        )
                    )
                )
            );
        }

        // 몸무게 받기
        String weight = loadJson.getJSONObject("action").getJSONObject("detailParams").getJSONObject("weight").optString("origin", "none");

        // 몸무게 값이 유효하지 않으면 return
        if (weight.equals("none") || !weight.matches("\\d+")) {
            return Map.of(
                "version", "2.0",
                "template", Map.of(
                    "outputs", new Object[]{
                        Map.of(
                            "simpleText", Map.of(
                                "text", "유효하지 않은 입력입니다."
                            )
                        )
                    }
                )
            );
        }

        Mservice.changeWeight(userName, Integer.parseInt(weight));

        return Map.of(
            "version", "2.0",
            "template", Map.of(
                "outputs", new Object[]{
                    Map.of(
                        "textCard", Map.of(
                            "title", "체중 기록을 완료하였습니다.",
                            "description", weight + "kg"
                        )
                    )
                }
            )
        );

    }
}
