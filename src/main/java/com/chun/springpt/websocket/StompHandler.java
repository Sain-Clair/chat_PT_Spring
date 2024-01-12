package com.chun.springpt.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.chun.springpt.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StompHandler implements ChannelInterceptor {

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

		if (StompCommand.CONNECT.equals(accessor.getCommand())) {
			String jwtToken = accessor.getFirstNativeHeader("Authorization");
			// JWT 토큰 검증 로직
			if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
				try {
					String token = JwtUtil.extractToken(jwtToken);
					if (!JwtUtil.isExpired(token)) {
			            String userName = JwtUtil.getUserName(token);
						// 사용자 인증 정보 설정
						// ... 인증 정보 설정 로직
					}
				} catch (Exception e) {
					log.error("Invalid Token", e);
					throw new IllegalArgumentException("Invalid Token");
				}
			}
		}
		return message;
	}
}
