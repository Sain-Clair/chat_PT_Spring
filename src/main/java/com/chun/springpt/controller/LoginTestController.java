package com.chun.springpt.controller;

import com.chun.springpt.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginTestController {

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/logintest")
    public ResponseEntity<String> loginTest(Authentication authentication) {

        // 헤더에서 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);

        // 사용자 아이디
        String userName = authentication.getName();

        // 사용자 권한
        String userRole = JwtUtil.getRole(token);

        return ResponseEntity.ok().body("유저 아이디 : " + userName + " 유저 권한 : " + userRole);
    }
}
