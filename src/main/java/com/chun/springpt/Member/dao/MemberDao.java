package com.chun.springpt.Member.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chun.springpt.Member.vo.MemberVO;



@Mapper
public interface MemberDao {
    
	public List<MemberVO> selectList();
}