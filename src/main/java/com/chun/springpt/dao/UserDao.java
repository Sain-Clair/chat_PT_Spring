package com.chun.springpt.dao;

import com.chun.springpt.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserDao {
    // 로그인
    UserVO loginCheck(@Param("id") String id, @Param("password") String password);

}