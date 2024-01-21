package com.chun.springpt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.service.MemberService;
import com.chun.springpt.utils.JwtUtil;
import com.chun.springpt.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;

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

}
