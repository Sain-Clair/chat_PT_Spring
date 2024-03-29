package com.chun.springpt.controller.kakaoChatbot;

import com.chun.springpt.service.kakaoChatbot.KakaoChatbotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/chatbot")
public class ChatbotJoinController {

    @Autowired
    private KakaoChatbotService kakaoChatbotService;

    @RequestMapping(value = "/join")
    @ResponseBody
    public Map<String, Object> join(@RequestBody String body) {

        String plusfriendUserKey = kakaoChatbotService.getPlusfriendUserKey(body);
        
        return Map.of(
            "version", "2.0",
            "template", Map.of(
                "outputs", List.of(
                    Map.of(
                        "textCard", Map.of(
                            "title", "카카오톡채널 연동 코드 :\n" + plusfriendUserKey,
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
}
