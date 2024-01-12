package com.chun.springpt.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.repository.ChatRoomRepository;
import com.chun.springpt.utils.JwtUtil;
import com.chun.springpt.vo.MessageVO;
import lombok.RequiredArgsConstructor;

/**
 * 채팅 메시지를 처리하는 컨트롤러 클래스입니다.
 * 
 * @RequiredArgsConstructor - Lombok 라이브러리의 어노테이션으로, final 또는 @NonNull 필드에 대한
 *                          생성자를 자동으로 생성합니다.
 */
@RequiredArgsConstructor
//@Controller
@RestController
public class MsgController {

	@Value("${jwt.secret}")
	private String secretKey;

	private final JwtUtil jwtUtil;

	// 메시지 전송을 위한 SimpMessageSendingOperations 의존성 주입.
	// 이 인터페이스는 메시지를 특정 대상에게 전송하는 메소드를 제공(stomp 통신 사용하면서 넣음)
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	// 채팅방 관련 데이터를 다루는 ChatRoomRepository의 의존성 주입. / DB와 연결하여 관리하기 위함
	@Autowired
	private ChatRoomRepository chatRoomRepository;

	/**
	 * 클라이언트로부터 메시지를 받아 처리하는 메소드입니다. "/chat/message" 경로로 메시지가 전송되면 이 메소드가 호출됩니다.
	 *
	 * @param messageVO - 클라이언트로부터 전송받은 메시지 데이터.
	 */
	@MessageMapping("/chat/message")
	public void message(@Payload MessageVO messageVO, @Header("token") String token) {

		// 메시지 타입이 ENTER(입장)인 경우, 입장 알림 메시지를 설정합니다.
		if (token == null || jwtUtil.isExpired(token, secretKey)) {
			// 토큰이 유효하지 않은 경우, 메시지 처리를 하지 않고 반환
			return;
		}
		// 토큰에서 사용자 이름 추출
		String username = jwtUtil.getUserName(token, secretKey);
		// 유효한 경우, 메시지 처리 계속
		// 기존 코드 ...

		if (MessageVO.MessageType.ENTER.equals(messageVO.getType())) {

			messageVO.setMessage(messageVO.getSender() + " 님이 온라인 상태입니다.");
		}

		// 해당 채팅방 구독자들에게 메시지를 전송
		// "/sub/chat/room/{roomId}" 경로로 메시지를 보냄으로써, 해당 채팅방에 있는 모든 클라이언트가 메시지를 받을 수
		// 있습니다.
		messagingTemplate.convertAndSend("/sub/chat/room/" + messageVO.getRoomId(), messageVO);

		// 채팅 메시지를 데이터베이스에 저장
		chatRoomRepository.insertMessage(messageVO);

		// 추가로 필요한 경우 다른 경로로 메시지를 전송할 수 있습니다.
		// 예를 들어, "/sub/{roomId}" 경로로 메시지를 전송하여, 다른 용도로 사용할 수 있습니다.
		messagingTemplate.convertAndSend("/sub/" + messageVO.getRoomId(), messageVO);
	}

	@GetMapping("/chat/rooms/{roomId}/messages")
	public List<MessageVO> getMessageById(@PathVariable String roomId) {
		return chatRoomRepository.getMessageById(roomId);
	}
}
