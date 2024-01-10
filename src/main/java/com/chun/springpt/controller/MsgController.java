package com.chun.springpt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.chun.springpt.repository.ChatRoomRepository;
import com.chun.springpt.vo.MessageVO;
import lombok.RequiredArgsConstructor;

//

@RequiredArgsConstructor
@Controller
public class MsgController {

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	@MessageMapping("/chat/message")
	public void message(@Payload MessageVO messageVO) {
		if (MessageVO.MessageType.ENTER.equals(messageVO.getType()))
			messageVO.setMessage(messageVO.getSender() + "님이 입장하셨습니다.");
		messagingTemplate.convertAndSend("/sub/chat/room/" + messageVO.getRoomId(), messageVO);
		chatRoomRepository.insertMessage(messageVO);
		
		messagingTemplate.convertAndSend("/sub/" + messageVO.getRoomId(), messageVO);
		
	}
	
//	이전코드
	/*
	 * @MessageMapping("/chat/message") public void message(@Payload MessageVO
	 * messageVO) { if (MessageVO.MessageType.ENTER.equals(messageVO.getType()))
	 * messageVO.setMessage(messageVO.getSender() + "님이 입장하셨습니다.");
	 * messagingTemplate.convertAndSend("/sub/chat/room/" + messageVO.getRoomId(),
	 * messageVO); chatRoomRepository.insertMessage(messageVO);
	 * 
	 * messagingTemplate.convertAndSend("/sub/" + messageVO.getRoomId(), messageVO);
	 * 
	 * }
	 */

}

/**
 * stomp 통신사용에 따라 기존 코드 삭제
 * 
 * 
 *
 * package com.chun.springpt.controller;
 * 
 * import java.util.List;
 * 
 * import org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import com.chun.springpt.dao.MessageDao; import
 * com.chun.springpt.service.MsgService; import com.chun.springpt.vo.MsgRoomVO;
 * 
 * import lombok.RequiredArgsConstructor;
 * 
 * @RestController
 * 
 * @RequiredArgsConstructor
 * 
 *                          @RequestMapping("/chat") public class MsgController
 *                          {
 * 
 *                          private final MsgService msgService;
 * 
 * @PostMapping public MsgRoomVO createRoom(@RequestParam String name) {
 *              System.out.println("!!!!채팅방생성!!!!!"); return
 *              msgService.createRoom(name);
 * 
 *              }
 * 
 * 
 * @GetMapping public List<MsgRoomVO> findAllRoom() {
 *             System.out.println("!!!!리스트불러오기!!!!" ); return
 *             msgService.findAllRoom(); } }
 */