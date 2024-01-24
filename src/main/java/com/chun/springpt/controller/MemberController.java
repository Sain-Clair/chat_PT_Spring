package com.chun.springpt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chun.springpt.service.MemberService;
import com.chun.springpt.utils.JwtUtil;
import com.chun.springpt.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class MemberController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MemberService Mservice;

    @GetMapping("/a_userList")
    public List<MemberVO> listMember() {
        return Mservice.selectMemberList();
    }

    @GetMapping("getMyRegion")
    public String getMyRegion() {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);
        String userName = JwtUtil.getID(token);

        return Mservice.getRegion(userName);
    }

    // 몸무게 수정
    @PostMapping("/changeWeight")
    @ResponseBody
    public String changeWeight(@RequestBody Map<String, String> map) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);
        String userName = JwtUtil.getID(token);

        Mservice.changeWeight(userName, Integer.parseInt(map.get("weight")));

        return "success";
    }

    // 특정 한명에 대한 정보 가져오기
    @GetMapping("/getuserInfo")
    @ResponseBody
    public List<MemberVO> getuserInfo(MemberVO memberVO) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);
        String userName = JwtUtil.getID(token);

        memberVO.setID(userName);

        return Mservice.getuserInfo(memberVO);
    }
    // 회원 정보 수정
    @PostMapping("/updateUserInfo")
    public ResponseEntity<?> updateUserInfo(@RequestBody MemberVO memberVO) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);
        String userName = JwtUtil.getID(token);

        memberVO.setID(userName); // 토큰에서 추출한 사용자 ID 설정
        System.out.println("유저 업데이트 요청! " + memberVO);

        try {
            Mservice.updateMemberInfo(memberVO); // 서비스 계층에서 회원 정보 업데이트
            System.out.println("유저 업데이트 완료: " + memberVO.getID());
            return ResponseEntity.ok().body("유저 업데이트 요청 완료 📀");
        } catch (Exception e) {
            System.err.println("유저 업데이트 실패: " + e.getMessage());
            return ResponseEntity.badRequest().body("유저 업데이트 요청 불가 ❌");
        }
    }

}
