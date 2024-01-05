package com.chun.springpt.dao;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

public interface MessageDao {
	List<MessageVO> getAllMessages();

	MessageVO getMessageById(Long id);

	void insertMessage(MessageVO message);

	void updateMessage(MessageVO message);

	void deleteMessage(Long id);
}