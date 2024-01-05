package com.chun.springpt.Member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.Member.service.MemberService;
import com.chun.springpt.Member.vo.MemberVO;

@RestController
public class MemberController {
	@Autowired
	private MemberService Mservice;
	@GetMapping("/a_userList")
	public List<MemberVO> listMember(){
		return Mservice.selectMemberList();
	}
}
 