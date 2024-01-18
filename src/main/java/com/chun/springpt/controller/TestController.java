package com.chun.springpt.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
public class TestController {
    @GetMapping("/hello")
    public String hello() {
        return "스프링boot ChatPT1 연결 확인!";
    }
}