package com.chun.springpt.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SignUpDao {
    // 이미 존재하는 이메일인지 확인
    int validCheckEmail(@Param("email") String email);

    int validCheckId(@Param("id") String id);   
}
