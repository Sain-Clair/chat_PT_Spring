package com.chun.springpt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.service.MemberService;
import com.chun.springpt.vo.MemberVO;

@RestController
public class MemberController {
	@Autowired
	private MemberService Mservice;
	
	
	@GetMapping("/a_userList")
	public List<MemberVO> listMember(){
		return Mservice.selectMemberList();
	}

	

}
 