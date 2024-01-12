package com.chun.springpt.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.chun.springpt.utils.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// JWT 관련 임포트 (필요한 경우)
// io.jsonwebtoken 패키지 관련 임포트는 JwtUtil 클래스에서 사용될 것입니다.
// 예시: import io.jsonwebtoken.Claims; (실제 사용하는 클래스에 따라 달라질 수 있습니다)

// 기타 필요한 임포트
// 예외 처리나 기타 로직에 필요한 추가적인 클래스들을 임포트합니다.

@Slf4j
@Component
public class StompHandler implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String jwtToken = accessor.getFirstNativeHeader("Authorization");
            
            if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
                String token = jwtToken.substring(7); // "Bearer " 이후의 문자열을 추출합니다.

                try {
                    if (!JwtUtil.isExpired(token)) {
                        System.out.println("1111111111111토큰이 유효함 / 정상 처리");
                    } else {
                    	System.out.println("2222222222222토큰이 만료된 경우 처리");
                        // 토큰이 만료된 경우 처리
                        throw new IllegalArgumentException("Expired token");
                    }
                } catch (Exception e) {
                    log.error("Invalid Token", e);
                	System.out.println("33333333333비정상적 접속 확인 | " + jwtToken);
                    throw new IllegalArgumentException("Invalid Token");
                    
                }
            }
        }

        return message;
    }
}