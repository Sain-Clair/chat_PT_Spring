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
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            String token = accessor.getFirstNativeHeader("token");
            if (token != null && !jwtUtil.isExpired(token)) {
                // 토큰이 유효한 경우 처리
            } else {
                // 토큰이 유효하지 않은 경우 예외 처리 또는 연결 거부
                throw new IllegalArgumentException("Invalid or expired token");
            }
        }

        return message;
    }
}
