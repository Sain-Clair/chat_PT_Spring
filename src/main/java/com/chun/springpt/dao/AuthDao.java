package com.chun.springpt.dao;

import com.chun.springpt.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface AuthDao {
    // 로그인
    UserVO loginCheck(@Param("id") String id, @Param("password") String password);

    // 카카오 로그인 (이메일을 통한)
    UserVO loginCheckWithEmail(@Param("email") String email);

    // 회원가입

    // 아이디 중복 체크

    // 이메일 유무 확인 및 권한 반환
    String checkEmailReturnId(@Param("email") String email);

    // 비밀번호 변경을 위한 회원 유무 확인
    UserVO userCheck(@Param("id") String id, @Param("name") String name, @Param("email") String email);

    // 비밀번호 변경
    void changePassword(@Param("id") String id, @Param("password") String newPassword);

    // 아이디 찾기
    String findId(@Param("name")String name, @Param("email")String email);

    // 회원 탈퇴

    // 카카오 연동코드로 회원의 닉네임 뽑아오기
    String getNicknameByPlusfriendUserKey(@Param("plusfriendUserKey") String plusfriendUserKey);

    // 카카오 연동코드로 회원의 ID 뽑아오기
    String getUserNameByPlusfriendUserKey(@Param("plusfriendUserKey") String plusfriendUserKey);
}