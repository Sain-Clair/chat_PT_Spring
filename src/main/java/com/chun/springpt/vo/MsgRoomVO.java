package com.chun.springpt.vo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import com.chun.springpt.service.MsgService;

import lombok.Builder;
import lombok.Data;

@Data
public class MsgRoomVO {
    private String roomId;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public MsgRoomVO(String roomId) {
        this.roomId = roomId;
    }

    public void handleActions(WebSocketSession session, MessageVO message, MsgService msgService) {
        if (message.getMessageType().equals(MessageVO.MessageType.ENTER)) {
            sessions.add(session);
            message.setLOG(message.getUSERID() + "님이 입장했습니다.");
        }
        sendMessage(message, msgService);
    }

    public <T> void sendMessage(T message, MsgService messageService) {
        sessions.parallelStream().forEach(session->messageService.sendMessage(session, message));
    }
}