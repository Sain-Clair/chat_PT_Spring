package com.chun.springpt.controller;

import com.chun.springpt.repository.ChatRoomRepository;
import com.chun.springpt.service.MemberService;
import com.chun.springpt.utils.JwtUtil;
import com.chun.springpt.vo.MsgRoomVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping
public class MatchptController {
  @Autowired
  private HttpServletRequest request;

  @Autowired
  private ChatRoomRepository chatRoomRepository;

  @PostMapping("/matching")
  public ResponseEntity<?> applyPTuser(@RequestBody MsgRoomVO msgRoomVO) {
    // 헤더에서 토큰 추출
    String authorizationHeader = request.getHeader("Authorization");
    String token = JwtUtil.extractToken(authorizationHeader);

    // 사용자 아이디
    String userId = JwtUtil.getUserName(token);
    msgRoomVO.setUserId(userId);

    // 사용자 권한
    String userRole = JwtUtil.getRole(token);
    System.out.println("유저이름 : " + userId + "/ 유저권한 : " + userRole);

    msgRoomVO.setStatus("wait");// 채팅방 상태 승인전까지 wait상태
    System.out.println(msgRoomVO.getRoomId() + " : 채팅방아이디");
    System.out.println(msgRoomVO.getTrainerId() + " : 트레이너아이디");
    System.out.println(msgRoomVO.getUserId() + " : 유저아이디");
    System.out.println(msgRoomVO.getStatus() + " : 채팅방상태(wait|live|expired)");


    try {
      chatRoomRepository.createChatRoom(msgRoomVO);
      return ResponseEntity.ok().body("PT신청 완료 📀");
    }catch (Exception e){
      return ResponseEntity.badRequest().body("PT신청 불가 ❌");
    }

  }
}
