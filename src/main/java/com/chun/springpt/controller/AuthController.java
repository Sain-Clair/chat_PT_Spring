package com.chun.springpt.controller;

import com.chun.springpt.domain.dto.LoginRequest;
import com.chun.springpt.service.AuthService;
import com.chun.springpt.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    @Autowired
    private HttpServletRequest request;

    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest dto) {
//        log.info("넘어온 아이디: {}",dto.getUserName());
//        log.info("넘어온 비밀번호: {}",dto.getPassword());
        return ResponseEntity.ok().body(authService.login(dto.getUserName(), dto.getPassword()));
    }

    @GetMapping("/checkToken")
    public ResponseEntity<?> checkToken() {
        // 헤더에서 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);

        // 사용자 권한
        String userRole = JwtUtil.getRole(token);

        return ResponseEntity.ok().body(userRole);
    }


}
