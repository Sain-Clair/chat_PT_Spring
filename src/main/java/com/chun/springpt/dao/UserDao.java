package com.chun.springpt.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserDao {
    // 권한 불러오기
    String getRole(@Param("id") String id);

    // 닉네임 불러오기
    String getNickname(@Param("id") String id);

    // 이름 불러오기
    String getName(@Param("id") String id);


}