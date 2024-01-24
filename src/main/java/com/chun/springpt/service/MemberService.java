package com.chun.springpt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chun.springpt.dao.MemberDao;
import com.chun.springpt.vo.MemberVO;

@Service
public class MemberService {
	
	@Autowired
	private MemberDao mdao;
	
	public List<MemberVO> selectMemberList(){
		return mdao.selectList();
	}
	public String getRegion(String userName){
		return mdao.getRegion(userName);
	}
    public void changeWeight(String userName, Integer weight) {
		mdao.changeWeight(userName, weight);
    }
	public List<MemberVO> getuserInfo(MemberVO memberVO){
		return mdao.getuserInfo(memberVO);
	}
}
