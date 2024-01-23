package com.chun.springpt.controller.kakaoChatbot;

import com.chun.springpt.service.kakaoChatbot.KakaoChatbotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@EnableAsync
@SuppressWarnings("unchecked")
@RequestMapping(value = "/chatbot")
public class ChatbotRecordController {

    @Autowired
    private KakaoChatbotService kakaoChatbotService;

    @RequestMapping("/record_meal")
    public void recordMeal(String body) {
    }
}
