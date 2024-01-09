package com.chun.springpt.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.chun.springpt.service.MsgService;
import com.chun.springpt.vo.MessageVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final MsgService msgService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ChatWebSocketHandler(MsgService msgService, ObjectMapper objectMapper) {
        this.msgService = msgService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        String payload = textMessage.getPayload();
        log.info("payload : {}", payload);

        try {
            MessageVO messageVO = objectMapper.readValue(payload, MessageVO.class);
            msgService.sendMessage(session, messageVO); // 메시지 처리 로직을 MsgService로 위임
        } catch (Exception e) {
            log.error("메시지 처리 중 오류 발생", e);
        }
    }
}

