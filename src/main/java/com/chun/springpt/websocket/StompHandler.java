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

/* 캡처 요먕 */

@Slf4j // Lombok 라이브러리의 로그기능을 사용하겠다는 어노테이션입니다. 클래스에 로그 객체를 자동으로 생성합니다.
@Component // 이 클래스를 스프링 컨테이너가 관리하는 빈(Bean)으로 등록합니다. 빈은 스프링이 제어하는 객체를 의미합니다.
public class StompHandler implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message); // STOMP 메시지의 헤더에 접근하기 위한 Accessor를 생성합니다.

        // 클라이언트가 서버에 연결할 때 실행되는 부분입니다.
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String jwtToken = accessor.getFirstNativeHeader("Authorization"); // STOMP 헤더에서 'Authorization' 헤더 값을 가져옵니다.

            // JWT 토큰이 존재하며 "Bearer "로 시작하는 경우
            if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
                String token = jwtToken.substring(7); // "Bearer " 다음부터의 문자열을 JWT 토큰으로 추출합니다.

                try {
                    // 토큰의 유효성을 검사합니다.
                    if (!JwtUtil.isExpired(token)) {
                        System.out.println("1 토큰이 유효함 / pass ");
                    } else {
                        // 토큰이 만료된 경우의 처리
                        System.out.println("2 토큰이 만료된 경우 처리 / deny");
                        throw new IllegalArgumentException("Expired token / ");
                    }
                } catch (Exception e) {
                    // 토큰이 유효하지 않은 경우의 예외 처리
                    log.error("Invalid Token", e);
                    System.out.println("3 비정상적 접속 확인 | " + jwtToken);
                    throw new IllegalArgumentException("Invalid Token");
                }
            }
        }
        return message; // 메시지를 반환합니다.
    }
}