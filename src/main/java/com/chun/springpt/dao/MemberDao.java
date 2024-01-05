package com.chun.springpt.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.chun.springpt.vo.MemberVO;

@Mapper
public interface MemberDao {
    
	public List<MemberVO> selectList();
}