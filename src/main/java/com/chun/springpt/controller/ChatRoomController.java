package com.chun.springpt.controller;

import java.util.List;

import com.chun.springpt.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chun.springpt.vo.MsgRoomVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

	private final com.chun.springpt.repository.ChatRoomRepository chatRoomRepository;

	@Autowired
	private JwtUtil jwtUtil;
	// 채팅 리스트 화면
	@GetMapping("/room")
	public String rooms(Model model) {
		return "/chat/room";
	}


	@Autowired
	private HttpServletRequest request;
	// 모든 채팅방 목록 반환 | 수정! Request Param값을 가지고 와서 특정 
	@GetMapping("/rooms")
	@ResponseBody
	public List<MsgRoomVO> room(MsgRoomVO msgRoomVO) {
		String authorizationHeader = request.getHeader("Authorization");
		String token = JwtUtil.extractToken(authorizationHeader);
		// 사용자 아이디
		String userName = JwtUtil.getID(token);

		msgRoomVO.setUserId(userName);
		return chatRoomRepository.findAllRoom(msgRoomVO); // selectAllChatRooms();
	}

	// 채팅방 생성
//	@PostMapping("/room")
//	@ResponseBody
//	public MsgRoomVO createRoom() {
//		return chatRoomRepository.createChatRoom(MsgRoomVO); // createChatRoom(name);
//	}

	// 채팅방 입장 화면
	@GetMapping("/room/enter/{roomId}")
	public String roomDetail(Model model, @PathVariable String roomId) {
//		System.out.println(roomId);
		model.addAttribute("roomId", roomId);

		return "/chat/roomdetail";
	}

	// 특정 채팅방 조회
	@GetMapping("/room/{roomId}")
	@ResponseBody
	public MsgRoomVO roomInfo(@PathVariable String roomId) {
		return chatRoomRepository.findRoomById(roomId);
	}
}