package com.chun.springpt.vo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.ibatis.type.Alias;
import org.springframework.web.socket.WebSocketSession;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Alias("msgroom")
@Data
public class MsgRoomVO {
    private String roomId;
		private String trainerId;
		private String userId;
		private String status; //("대기 : wait ? 승인 : live ? 만료 : expired ")
		private Date ptstart;
    private Set<WebSocketSession> sessions = new HashSet<>();

		private String name;

    public static MsgRoomVO create(String name) {
    	MsgRoomVO msgRoomVO = new MsgRoomVO();
    	msgRoomVO.roomId = UUID.randomUUID().toString();

    	
		return msgRoomVO;
	}
}


/*
 * package com.chun.springpt.vo;
 * 
 * import java.util.HashSet; import java.util.Set; import
 * org.apache.ibatis.type.Alias; import
 * org.springframework.web.socket.WebSocketSession; import
 * com.chun.springpt.service.MsgService; import lombok.Builder; import
 * lombok.Data; import lombok.Getter;
 * 
 * @Alias("msgroom")
 * 
 * @Data public class MsgRoomVO { private String roomId; private String name;
 * private Set<WebSocketSession> sessions = new HashSet<>();
 * 
 * @Builder public MsgRoomVO(String roomId, String name) { this.roomId = roomId;
 * this.name = name; }
 * 
 * public void handleActions(WebSocketSession session, MessageVO message,
 * MsgService msgService) { if
 * (message.getType().equals(MessageVO.MessageType.ENTER)) {
 * sessions.add(session); message.setMessage(message.getSender() +
 * "님이 온라인 상태입니다."); } sendMessage(message, msgService); }
 * 
 * public <T> void sendMessage(T message, MsgService messageService) {
 * sessions.parallelStream().forEach(session->messageService.sendMessage(
 * session, message)); } }
 */