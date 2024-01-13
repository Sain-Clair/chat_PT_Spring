package com.chun.springpt.controller;

import com.chun.springpt.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenTestController {

    @Autowired
    private HttpServletRequest request;

    // 토큰을 핸들링하는 컨트롤러
   @PostMapping("/logintest")
    public ResponseEntity<String> loginTest() {

        // 헤더에서 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);

        // 사용자 아이디
        String userName = JwtUtil.getID(token);

        // 사용자 권한
        String userRole = JwtUtil.getRole(token);

        return ResponseEntity.ok().body("유저 아이디 : " + userName + " 유저 권한 : " + userRole);
    }

    // 토큰의 유효성 검사만 하고 토큰을 핸들링 하지는 않음
    @PostMapping("/logintest2")
    public ResponseEntity<?> loginTest2() {
        return ResponseEntity.ok().body("유효한 토큰 잘 들어와서 통신 성공");
    }
}
