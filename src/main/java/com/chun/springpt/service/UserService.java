package com.chun.springpt.service;

import com.chun.springpt.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Value("${jwt.secret}")
    private String secretKey;

    private Long expiredMs = 1000 * 60 * 60L; // 1시간

    public String login(String userID, String password) {
        return JwtUtil.createJwt(userID, secretKey, expiredMs);
    }
}
