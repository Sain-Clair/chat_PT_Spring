package com.chun.springpt.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.chun.springpt.vo.MessageVO;
import com.chun.springpt.vo.MsgRoomVO;

import java.util.List;

@Mapper
@Repository
public interface MessageDao {
	List<MessageVO> getAllMessages();

	MessageVO getMessageById(Long id);

	void insertMessage(MessageVO message); // 메시지 전송

	void updateMessage(MessageVO message);

	void deleteMessage(Long id);
	
    
    public void insertChatRoom(MsgRoomVO chatRoom); // 챗룸 생성
    public MsgRoomVO selectChatRoomById(String roomId); // 챗룸 하나 선택
    public List<MsgRoomVO> selectAllChatRooms(); // 전체 챗룸 선택
    public void deleteChatRoom(String roomId); // 챗룸 삭제
}