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

    // 번호 가져오기

    int getMyNum(@Param("id") String id);

    // 트레이너 승인 확인 번호
    int getisBool(@Param("id") String id);


}