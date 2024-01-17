package com.chun.springpt.controller;

import com.chun.springpt.dao.PthandleDao;
import com.chun.springpt.service.PthandleService;
import com.chun.springpt.utils.JwtUtil;
import com.chun.springpt.vo.PthandleVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PthandleControl {
  @Autowired
  private HttpServletRequest request;
  @Autowired
  private PthandleService pthandleService;
  @GetMapping("/pthandleAll")
  @ResponseBody
  public List<PthandleVO> pthandleAll(){
    System.out.println();
    String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);
    // 사용자 아이디
    String userName = JwtUtil.getID(token);

    PthandleVO pthandleVO = new PthandleVO();
    pthandleVO.setTRAINERID(userName); // username을 TRAINERID 필드에 설정

    System.out.println(userName);
    return pthandleService.ptAllList(pthandleVO);
  }
//
  @PostMapping("/pthandleToLive")
  public ResponseEntity<?> pthandleToLive(@RequestBody PthandleVO pthandleVO) {
    try {
      pthandleService.pthandleToLive(pthandleVO);
      return ResponseEntity.ok("예약이 확인되었습니다.");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예약 확인 중 오류가 발생했습니다.");
    }
  }
}
