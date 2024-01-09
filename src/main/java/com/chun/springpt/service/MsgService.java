package com.chun.springpt.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.chun.springpt.dao.MessageDao;
import com.chun.springpt.vo.MessageVO;
import com.chun.springpt.vo.MsgRoomVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MsgService {

	private final ObjectMapper objectMapper;
	private final MessageDao messageDao;
	private Map<String, MsgRoomVO> msgRooms; 
	
	@Autowired
	private MessageDao msgdao; // chatroom + chatMessage

	@PostConstruct
	private void init() {
		msgRooms = new LinkedHashMap<>();
	}

	public List<MsgRoomVO> findAllRoom() {
		return msgdao.selectAllChatRooms();
	}

	public MsgRoomVO findById(String roomId) {
		return msgdao.selectChatRoomById(roomId);
	}

//    public MsgRoomVO createRoom(String name) {
//    	return msgdao.insertRoom();
//    	
//    	//        int roomId = name;
////        msgRooms.put(roomId, MsgRoomVO.builder().roomId(roomId).build()); // MsgRoomVO 생성 후 맵에 추가
////        return msgRooms.get(roomId); // 해당 roomId에 대한 MsgRoomVO 반환
//    }

	// 마이바티스 DB연동/ 방만들기
	public MsgRoomVO createRoom(String name) {
		String randomId = UUID.randomUUID().toString();
		MsgRoomVO chatRoom = new MsgRoomVO(randomId, name);
		msgRooms.put(randomId, chatRoom);
		// 생성된 채팅방을 데이터베이스에 저장합니다.
		msgdao.insertChatRoom(chatRoom);
		return chatRoom;
	}

	// 마이바티스 DB연동/ 메시지 보내기
	public <T> void sendMessage(WebSocketSession session, T message) {
		try {

			if (message instanceof MessageVO) {
				MessageVO messageVO = (MessageVO) message;
	            messageVO.setLogdate(new Date()); // 현재 시간 설정
	            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(messageVO)));
	            System.out.println("메시지 전송!!!!");

	            msgdao.insertMessage(messageVO); // 메시지를 DB에 저장
	            System.out.println(msgdao + " !!!메시지 DB저장 완료!!!");
	        }
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
