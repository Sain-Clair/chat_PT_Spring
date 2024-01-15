package com.chun.springpt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.service.SignUpService;

@RestController
public class SignUpController {
    @Autowired
    private SignUpService signUpService;

    // 이메일 중복 체크
    @PostMapping("/signUp/email/{email}")
    public int emailCheck(@PathVariable("email") String email) {
        return signUpService.validCheckEmail(email);
    }
    // id 중복 체크
    @PostMapping("/signUp/id/{id}")
    public int checkId(@PathVariable("id") String id){
        return signUpService.validCheckId(id);
    }
}
