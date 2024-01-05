package com.chun.springpt.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.chun.springpt.service.MsgService;
import com.chun.springpt.vo.MessageVO;
import com.chun.springpt.vo.MsgRoomVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

	private final MsgService msgService;

	// 생성자를 통해 MsgService 주입
	public ChatWebSocketHandler(MsgService msgService) {
		this.msgService = msgService;
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		log.info("payload : {}", payload);

		MessageVO msg;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			msg = objectMapper.readValue(payload, MessageVO.class);
			MsgRoomVO room = msgService.findById(msg.getCHATROOMID());
			room.handleActions(session, msg, msgService);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

//        TextMessage initialGreeting = new TextMessage("Welcome to Swoomi Chat Server ~O_O~");
//        session.sendMessage(initialGreeting);
	}
}
