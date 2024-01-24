package com.chun.springpt.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SignUpDao {
    // 가입
    int insertMembers(Map<String, Object> data);

    int insertNormal(Map<String, Object> data);
    // 시퀀스 가져오기
    int selectSeq(Map<String, Object> data);

    int insertMemFood(Map<String, Object> data);

    int insertTrainer(Map<String, Object> data);

    int updatePTimage(Map<String, Object> data);

    // 이미 존재하는 이메일인지 확인
    int validCheckEmail(@Param("email") String email);

    // 아이디 중복체크
    int validCheckId(@Param("id") String id);
    

}
