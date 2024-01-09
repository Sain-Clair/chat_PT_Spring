package com.chun.springpt.dao;

import com.chun.springpt.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    UserVO findById(String id);
}
