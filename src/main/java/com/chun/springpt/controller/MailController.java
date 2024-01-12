package com.chun.springpt.controller;

import com.chun.springpt.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @GetMapping("/getmail")
    public String MailPage() {
        return "mail";
    }

    @ResponseBody
    @PostMapping("/postmail")
    public String MailSend(String mail) {
        int number = mailService.sendMail(mail);

        String num = "" + number;

        return num;
    }
}
