package com.chun.springpt.controller.kakaoChatbot;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/chatbot")
public class ChatbotTestController {

    @RequestMapping(value = "/connectionTest")
    @ResponseBody
    public Map<String, Object> connectionTest() {

        return Map.of(
            "version", "2.0",
            "template", Map.of(
                "outputs", new Object[]{
                    Map.of(
                        "simpleText", Map.of(
                            "text", "연결됨."
                        )
                    )
                }
            )
        );

    }
}
