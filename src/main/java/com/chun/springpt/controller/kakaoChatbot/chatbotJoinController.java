package com.chun.springpt.controller.kakaoChatbot;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/chatbot")
public class chatbotJoinController {
    @RequestMapping(value = "/join")
    @ResponseBody
    public Map<String, Object> join(@RequestBody String body) {
        JSONObject loadJson = new JSONObject(body);

        String plusfriendUserKey = loadJson.optJSONObject("userRequest").optJSONObject("user").optJSONObject("properties").optString("plusfriend_user_key");
        System.out.println("바디" + loadJson);
        System.out.println("플플" + plusfriendUserKey);
        
        return Map.of(
            "version", "2.0",
            "template", Map.of(
                "outputs", List.of(
                    Map.of(
                        "textCard", Map.of(
                            "title", "카카오톡채널 연동 코드 :\n" + plusfriendUserKey,
                            "description", "코드를 회원가입시 입력해주세요.\n",
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
}
