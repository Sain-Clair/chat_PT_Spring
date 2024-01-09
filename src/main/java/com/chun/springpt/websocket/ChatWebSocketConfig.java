package com.chun.springpt.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSocket
public class ChatWebSocketConfig implements WebSocketConfigurer {

	private final ChatWebSocketHandler chatWebSocketHandler; // 의존성 주입을 위해 필드 추가

	public ChatWebSocketConfig(ChatWebSocketHandler chatWebSocketHandler) {
		this.chatWebSocketHandler = chatWebSocketHandler;
	}

	@Override // 챗, 채팅 주소 : ws://localhost/chatptCHAT #챗,채팅,
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(chatWebSocketHandler, "/chatptCHAT").setAllowedOrigins("*");
	}

	@Override // 챗, 채팅 주소 : ws://localhost/chatptCHAT-stomp #챗,채팅, 스톰프기능추가
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chatptCHAT-stomp").setAllowedOrigins("*").withSockJS();

	}
}