package com.chun.springpt.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.service.SignUpService;

@RestController
public class SignUpController {
    @Autowired
    private SignUpService signUpService;

    // id 중복 체크
    @PostMapping("/signUp/id")
    public ResponseEntity<String> checkId(@RequestBody Map<String, String> data) {
        String id = data.get("id");
        int num = signUpService.validCheckId(id);
        if (num < 1) {
            return ResponseEntity.ok().body("사용가능");
        } else {
            return ResponseEntity.ok().body("사용 불가");
        }
    }
    // 이메일 중복 체크
    @PostMapping("/signUp/email")
    public ResponseEntity<String> emailCheck(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        int num = signUpService.validCheckEmail(email);
        if (num < 1) {
            return ResponseEntity.ok().body("사용가능");
        } else {
            return ResponseEntity.ok().body("사용 불가");
        }
    }

}
