package com.chun.springpt.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.chun.springpt.vo.MsgRoomVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MsgService {
    private final ObjectMapper objectMapper;
    private Map<String, MsgRoomVO> msgRooms;

    @PostConstruct
    private void init() {
        msgRooms = new LinkedHashMap<>();
    }

    public List<MsgRoomVO> findAllRoom() {
        return new ArrayList<>(msgRooms.values());
    }

    public MsgRoomVO findById(String roomId) {
        return msgRooms.get(roomId);
    }

    public MsgRoomVO createRoom(String name) {
        
        String roomId = name;
        return MsgRoomVO.builder().roomId(roomId).build();
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}