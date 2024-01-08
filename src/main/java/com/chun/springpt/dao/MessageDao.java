package com.chun.springpt.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.chun.springpt.vo.MessageVO;
import com.chun.springpt.vo.MsgRoomVO;

import java.util.List;

@Mapper
public interface MessageDao {
	List<MessageVO> getAllMessages();

	MessageVO getMessageById(Long id);

	void insertMessage(MessageVO message);

	void updateMessage(MessageVO message);

	void deleteMessage(Long id);
		
    public MsgRoomVO insertRoom();
    
    public List<MsgRoomVO> roomList();

}