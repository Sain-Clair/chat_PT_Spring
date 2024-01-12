package com.chun.springpt.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class ChatWebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private final StompHandler stompHandler;
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/sub"); // 구독자
		config.setApplicationDestinationPrefixes("/pub"); // 퍼블리셔
	}

	@Override // 챗, 채팅 주소 : ws://localhost/chatptCHAT #챗,채팅, >>>> .SockJS로
				// http://localhost/ws-stomp

	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*").withSockJS();
	}

	@Override // Jackson 으로 JSON을 핸들링 하여, 변환을 위함
	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
		messageConverters.add(new MappingJackson2MessageConverter());
		return true;
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(stompHandler);
	}
}

/* stomp설정 전 이전 코드 */

/*
 * package com.chun.springpt.websocket;
 * 
 * import org.springframework.context.annotation.Configuration; import
 * org.springframework.web.socket.config.annotation.EnableWebSocket; import
 * org.springframework.web.socket.config.annotation.WebSocketConfigurer; import
 * org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
 * 
 * import lombok.RequiredArgsConstructor; import lombok.extern.slf4j.Slf4j;
 * 
 * @Slf4j
 * 
 * @Configuration
 * 
 * @EnableWebSocket public class ChatWebSocketConfig implements
 * WebSocketConfigurer {
 * 
 * private final ChatWebSocketHandler chatWebSocketHandler; // 의존성 주입을 위해 필드 추가
 * 
 * public ChatWebSocketConfig(ChatWebSocketHandler chatWebSocketHandler) {
 * this.chatWebSocketHandler = chatWebSocketHandler; }
 * 
 * @Override //챗, 채팅 주소 : ws://localhost/chatptCHAT #챗,채팅, public void
 * registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
 * registry.addHandler(chatWebSocketHandler,
 * "/chatptCHAT").setAllowedOrigins("*"); } }
 */