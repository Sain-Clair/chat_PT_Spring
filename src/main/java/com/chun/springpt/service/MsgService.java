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
    private Map<Integer, MsgRoomVO> msgRooms; // roomId를 Integer 타입으로 변경

    @PostConstruct
    private void init() {
        msgRooms = new LinkedHashMap<>();
    }

    public List<MsgRoomVO> findAllRoom() {
        return new ArrayList<>(msgRooms.values());
    }

    public MsgRoomVO findById(int roomId) {
        return msgRooms.get(roomId);
    }

    public MsgRoomVO createRoom(int name) {
        int roomId = name;
        msgRooms.put(roomId, MsgRoomVO.builder().roomId(roomId).build()); // MsgRoomVO 생성 후 맵에 추가
        return msgRooms.get(roomId); // 해당 roomId에 대한 MsgRoomVO 반환
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
