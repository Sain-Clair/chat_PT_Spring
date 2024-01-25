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


/* 캡처 요먕 */
@Slf4j // Lombok의 로깅을 위한 어노테이션. 클래스에 자동으로 로그 객체를 생성합니다.
@RequiredArgsConstructor // Lombok을 사용하여 final이나 @NonNull 필드에 대한 생성자를 자동으로 생성합니다.
@Configuration // 이 클래스가 스프링의 구성(Configuration) 클래스임을 나타냅니다.
@EnableWebSocketMessageBroker // WebSocket 메시지 브로커를 사용하도록 설정하는 어노테이션입니다.
public class ChatWebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private final StompHandler stompHandler; // STOMP 프로토콜을 처리하기 위한 핸들러

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// 메시지 브로커가 "/sub"로 시작하는 대상에게 메시지를 전달하도록 설정합니다.
		config.enableSimpleBroker("/sub");
		// 클라이언트가 "/pub"로 시작하는 대상으로 메시지를 보내면 애플리케이션의 @Controller가 처리하도록 설정합니다.
		config.setApplicationDestinationPrefixes("/pub");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// STOMP 프로토콜을 사용하기 위한 엔드포인트를 "/ws-stomp"로 설정합니다.
		// SockJS 지원을 활성화하여 WebSocket이 사용할 수 없는 경우에 대체 옵션을 제공합니다.
		registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*").withSockJS();
	}

	@Override
	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
		// JSON 메시지를 처리하기 위해 Jackson 라이브러리를 사용하는 메시지 컨버터를 추가합니다.
		messageConverters.add(new MappingJackson2MessageConverter());
		return true;
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		// 클라이언트로부터 오는 메시지를 처리하기 위해 StompHandler 인터셉터를 추가합니다.
		registration.interceptors(stompHandler);
	}

}