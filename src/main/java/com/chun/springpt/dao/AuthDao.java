package com.chun.springpt.dao;

import com.chun.springpt.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface AuthDao {
    // 로그인
    UserVO loginCheck(@Param("id") String id, @Param("password") String password);


    // 회원가입

    // 아이디 중복 체크

    // 이메일 중복 체크

    // 비밀번호 변경

    // 회원 탈퇴

    // 아이디 찾기
    String findId(@Param("name")String name, @Param("email")String email);

    // 비밀번호 찾기 후 비밀번호 변경

}