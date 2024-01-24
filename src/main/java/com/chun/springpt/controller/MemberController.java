package com.chun.springpt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

    // 특정 한명에 대한 정보 가져오기
    @GetMapping("/getuserInfo")
    public List<MemberVO> getuserInfo() {
        String authorizationHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authorizationHeader);
        String userName = JwtUtil.getID(token);

        return Mservice.getuserInfo(userName);
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

}
