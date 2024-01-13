package com.chun.springpt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.service.SignUpService;

@RestController
public class SignUpController {
    @Autowired
    private SignUpService signUpService;
    @PostMapping("/validCheckEmail/{email}")
    public String emailCheck(@PathVariable("email") String email) {
        return signUpService.validCheckEmail(email);
    }
    
}
