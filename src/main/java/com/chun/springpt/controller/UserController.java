package com.chun.springpt.controller;

import com.chun.springpt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok().body(userService.login("",""));
    }
}
