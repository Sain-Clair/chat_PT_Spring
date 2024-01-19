package com.chun.springpt.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SignUpDao {
    // 이미 존재하는 이메일인지 확인
    int validCheckEmail(@Param("email") String email);

    // 아이디 중복체크
    int validCheckId(@Param("id") String id);

    // 가입 return
    int insertMembers(Map<String, String> data);
}
