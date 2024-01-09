package com.chun.springpt.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.chun.springpt.mapper.ChatMapper;
import com.chun.springpt.vo.MessageVO;
import com.chun.springpt.vo.MsgRoomVO;

@Repository
public class ChatRoomRepository {

	private final ChatMapper chatMapper;

	@Autowired
	public ChatRoomRepository(ChatMapper chatRoomMapper) {
		this.chatMapper = chatRoomMapper;
	}

	public List<MsgRoomVO> findAllRoom() {
		return chatMapper.findAllRoom();
	}

	public MsgRoomVO findRoomById(String roomId) {
		return chatMapper.findRoomById(roomId);
	}
	
	public void insertMessage(MessageVO messageVO) {
        chatMapper.insertMessage(messageVO);
    }
		
	

	public MsgRoomVO createChatRoom(String name) {
		MsgRoomVO msgRoomVO = new MsgRoomVO();
		msgRoomVO.setRoomId(UUID.randomUUID().toString());
		msgRoomVO.setName(name);
		chatMapper.insertChatRoom(msgRoomVO);
		return msgRoomVO;
	}

	
}