package com.chun.springpt.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.utils.JwtUtil;
import com.chun.springpt.vo.LoginInfo;
import com.chun.springpt.vo.MsgRoomVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {

	private final JwtUtil jwtUtil;

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

	// 채팅방 입장 화면
	@GetMapping("/room/enter/{roomId}")
	public String roomDetail(Model model, @PathVariable String roomId) {
		model.addAttribute("roomId", roomId);
		return "/chat/roomdetail";
	}

	// 특정 채팅방 조회
	@GetMapping("/room/{roomId}")
	@ResponseBody
	public MsgRoomVO roomInfo(@PathVariable String roomId) {
		return chatRoomRepository.findRoomById(roomId);
	}
	
	@GetMapping("/user")
    @ResponseBody
    public LoginInfo getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        String role = auth.getAuthorities().isEmpty() ? "USER" : auth.getAuthorities().iterator().next().getAuthority();

        // JWT 토큰 생성 시 만료 시간 설정 (예: 1시간 = 3600000ms)
        long expireMs = 3600000;
        String token = jwtUtil.createJwt(name, role, null);

        return LoginInfo.builder().name(name).token(token).build();
    }
}