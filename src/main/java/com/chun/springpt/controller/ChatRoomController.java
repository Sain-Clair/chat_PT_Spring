package com.chun.springpt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
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

	// 채팅 리스트 화면
	@GetMapping("/room")
	public String rooms(Model model) {
		return "/chat/room";
	}

	// 모든 채팅방 목록 반환 | 수정! Request Param값을 가지고 와서 특정 
	@GetMapping("/rooms")
	@ResponseBody
	public List<MsgRoomVO> room() {
		return chatRoomRepository.findAllRoom(); // selectAllChatRooms();
	}

	// 채팅방 생성
	@PostMapping("/room")
	@ResponseBody
	public MsgRoomVO createRoom(@RequestParam String name) {
		return chatRoomRepository.createChatRoom(name); // createChatRoom(name);
	}
	
	
	@GetMapping("/room/enter/{roomId}")
	public ResponseEntity<?> roomDetail(@PathVariable String roomId) {
	    // 필요한 로직 수행
	    // 예를 들어, roomId에 해당하는 방의 상세 정보를 조회하는 로직 등

	    // roomId를 포함한 다른 정보들을 JSON 형식으로 반환
	    Map<String, Object> result = new HashMap<>();
	    result.put("roomId", roomId);
	    // 여기에 더 많은 데이터를 추가할 수 있습니다.

	    return ResponseEntity.ok(result);
	}

	// 채팅방 입장 화면 이전 코드
//	@GetMapping("/room/enter/{roomId}")
//	public String roomDetail(Model model, @PathVariable String roomId) {
//		model.addAttribute("roomId", roomId);
//		return "/chat/roomdetail";
//	}

	// 특정 채팅방 조회
	@GetMapping("/room/{roomId}")
	@ResponseBody
	public MsgRoomVO roomInfo(@PathVariable String roomId) {
		return chatRoomRepository.findRoomById(roomId);
	}
}