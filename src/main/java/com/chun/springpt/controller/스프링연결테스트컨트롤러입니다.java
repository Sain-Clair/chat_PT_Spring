package com.chun.springpt.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class 스프링연결테스트컨트롤러입니다 {
    @GetMapping("/hello")
    public String hello() {
        return "스프링boot ChatPT1 연결 확인!";
    }
}