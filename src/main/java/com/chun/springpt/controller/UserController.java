package com.chun.springpt.controller;

import com.chun.springpt.domain.dto.LoginRequest;
import com.chun.springpt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest dto) {
//        log.info("넘어온 아이디: {}",dto.getUserName());
//        log.info("넘어온 비밀번호: {}",dto.getPassword());
        return ResponseEntity.ok().body(userService.login(dto.getUserName(), dto.getPassword()));
    }
}
