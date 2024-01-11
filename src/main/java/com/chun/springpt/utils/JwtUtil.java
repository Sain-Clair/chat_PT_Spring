package com.chun.springpt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {


    //    private static String secretKey;
    @Value("${jwt.secret}")
    private static String secretKey;

    // 헤더에서 토큰 추출하는 메서드
    public static String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // "Bearer " 다음의 토큰 값만 추출
        }
        return null;
    }

    // 토큰에서 userName 가져오기
    public static String getUserName(String token, String secretKey) {
        JwtUtil.secretKey = secretKey;
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("userName", String.class);
    }

    // 토큰에서 id 가져오기
    public static String getID(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("userName", String.class);
    }


    // 토큰에서 role 가져오기
    public static String getRole(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("role", String.class);
    }

    // 토큰이 만료되었는지 확인
    // true: 만료됨
    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public static String createJwt(String userName, String role, String secretKey, Long expireMs) {
        Claims claims = Jwts.claims();
        claims.put("userName", userName);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
