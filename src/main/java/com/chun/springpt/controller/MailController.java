package com.chun.springpt.controller;

import com.chun.springpt.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @ResponseBody
    @PostMapping("/postmail")
    public String MailSend(String mail) {
        int number = mailService.sendMail(mail);

        return "" + number;
    }
}
