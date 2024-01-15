package com.chun.springpt.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.chun.springpt.mapper.ChatMapper;
import com.chun.springpt.vo.MessageVO;
import com.chun.springpt.vo.MsgRoomVO;


//spring의 의존성주입 | 비즈니스 로직을 관리, 작은 규모이며 트랜잭션 관리는 안할것이므로 @service를 따로 만들지 않음
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

  public MsgRoomVO createChatRoom(MsgRoomVO msgRoomVO) {
    msgRoomVO.setRoomId(UUID.randomUUID().toString());
    msgRoomVO.setStatus("wait");// 채팅방 상태 승인전까지 wait상태

    chatMapper.insertChatRoom(msgRoomVO);
    return msgRoomVO;
  }

  public void deleteChatRoom(MsgRoomVO msgRoomVO) {

    chatMapper.deleteChatRoom(msgRoomVO);
  }


  public void insertMessage(MessageVO messageVO) {
    chatMapper.insertMessage(messageVO);
  }

  public List<MessageVO> getMessageById(String roomId) {
    return chatMapper.getMessageById(roomId);
  }


}