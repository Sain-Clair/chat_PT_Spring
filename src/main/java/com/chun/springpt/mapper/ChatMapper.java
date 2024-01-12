package com.chun.springpt.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chun.springpt.vo.MessageVO;
import com.chun.springpt.vo.MsgRoomVO;

import java.util.List;

@Mapper // Dao의 역활 |  mybatis 접속 로직을 관리 
public interface ChatMapper {
  List<MessageVO> getAllMessages();

  public List<MessageVO> getMessageById(String roomId); // 메시지 로그 가져오기

  public void insertMessage(MessageVO message); // 메시지 전송

  void updateMessage(MessageVO message);

  void deleteMessage(Long id);

  public void insertChatRoom(MsgRoomVO chatRoom); // 챗룸 생성

  public MsgRoomVO findRoomById(String roomId); // 챗룸 하나 선택

  public List<MsgRoomVO> findAllRoom(); // 전체 챗룸 선택

  public void deleteChatRoom(String roomId); // 챗룸 삭제
}


