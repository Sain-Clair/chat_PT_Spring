package com.chun.springpt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chun.springpt.vo.MemberVO;

@Mapper
public interface MemberDao {
	public List<MemberVO> selectList();

	public String getRegion(String userName);
    void changeWeight(String userName, Integer weight);
	public int getMyNum(String id);

	//유저 정보 전부 가져오기
	public List<MemberVO> getuserInfo(MemberVO memberVO);


}