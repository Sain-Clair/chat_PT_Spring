package com.chun.springpt.controller;

import com.chun.springpt.security.dto.AuthenticationRequest;
import com.chun.springpt.security.dto.AuthenticationResponse;
import com.chun.springpt.security.utils.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // authenticateUser
    // 사용자가 제공한 로그인 정보(username, password)를 검증하고, 유효하면 JWT 토큰을 생성하여 반환
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        // UsernamePasswordAuthenticationToken을 생성해서 AuthenticationManager에 전달
        Authentication authentication = authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(authenticationRequest.getId(), authenticationRequest.getPassword()));
        // 인증 성공 시, SecurityContextHolder에 인증 정보를 설정한다.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // JwtTokenProvider를 사용하여 JWT 토큰을 생성
        String jwt = jwtTokenProvider.createToken(authentication);
        System.out.print("jwt =>" + jwt);
        // AuthenticationResponse에 JWT 토큰을 담아 클라이언트에게 반환
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    // 사용자 로그아웃 처리
    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        // 실제로 JWT 토큰 기반 인증에서 로그아웃은 클라이언트 측에서 토큰을 삭제하는 것으로 처리되지만,
        // 서버 측에서는 SecurityContextHolder의 컨텍스트를 클리어 해야 한다.
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout successful");
    }

    // http://localhost/springjwt1/api/auth/test
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("접근이 됨을 확인합니다.");
    }
}
