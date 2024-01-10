package com.chun.springpt.service;

import com.chun.springpt.dao.UserDao;
import com.chun.springpt.utils.JwtUtil;
import com.chun.springpt.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    // 권한 조회
    public String getRole(String userName) {
        return userDao.getRole(userName);
    }

    // 닉네임 조회
    public String getNickname(String userName) {
        return userDao.getNickname(userName);
    }

    // 이름 조회
    public String getName(String userName) {
        return userDao.getName(userName);
    }
}
