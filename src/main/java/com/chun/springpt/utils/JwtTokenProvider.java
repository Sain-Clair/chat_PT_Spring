package com.chun.springpt.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

// @Slf4j 어노테이션: Lombok 라이브러리에서 제공하는 어노테이션으로, 로깅을 위한 어노테이션
@Component
@Slf4j
public class JwtTokenProvider {

    //    private static String secretKey;
    @Value("${jwt.secret}")
    private static String secretKey;

    // 인증된 사용자에 대한 JWT를 생성을 하고
    public String createToken(Authentication authentication) {

        // 스프링 시큐리티에서 Authentication 객체로부터
        // 사용자의 UserDetail을 얻어 사용자 이름을 주제로 설정하고
        // 현재 시간과 만료 시간을 포함한 토큰을 생성
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {

        try {

            // 토큰을 파싱하여 정상적인 토큰인지 확인
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            // 토큰값이 유효하면 true를 리턴
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token : 토큰의 형식이 올바르지 않음 ");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token : 토큰이 만료되었을 때 발생");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token : 지원되지 않는 토큰 유형일 때 발생");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty : 토큰이 비어있거나 null일 때 발생");
        } catch (SignatureException e) {
            log.error("there is an error with the signature of you token : 토큰의 서명이 유효하지 않을 때 발생");
        }

        return false;
    }

    // 새로운 방식으로 키 설정 및 토큰 파싱
    public String getUsername(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}