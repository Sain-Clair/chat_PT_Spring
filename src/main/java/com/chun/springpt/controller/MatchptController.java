package com.chun.springpt.controller;

import com.chun.springpt.repository.ChatRoomRepository;
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
    String userName = JwtUtil.getID(token);
    // 사용자 권한
    String userRole = JwtUtil.getRole(token);
    System.out.println("유저아이디 : " + userName + "/ 유저권한 : " + userRole);
    System.out.println(msgRoomVO.getTrainerId());


    //chatRoomRepository.createChatRoom(name);

    return ResponseEntity.ok().body("백엔드 / PT신청 완료");
  }
}
