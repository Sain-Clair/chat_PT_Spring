package com.chun.springpt.Member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chun.springpt.Member.dao.MemberDao;
import com.chun.springpt.Member.vo.MemberVO;



@Service
public class MemberService {
	
	@Autowired
	private MemberDao mdao;
	
	public List<MemberVO> selectMemberList(){
		return mdao.selectList();
	}

}
