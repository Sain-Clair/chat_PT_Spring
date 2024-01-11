package com.chun.springpt.service;

import com.chun.springpt.dao.AuthDao;
import com.chun.springpt.utils.JwtUtil;
import com.chun.springpt.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthDao authDao;

    @Value("${jwt.secret}")
    private String secretKey;

    private Long expiredMs = 1000 * 60 * 60L; // 1시간


    // 로그인
    // getUser()를 통해 유저객체를 받아올 수 있을 시(데이터베이스에 유저가 존재할 시) 토큰 반환
    public String login(String userName, String password) {
        UserVO userVO = getUser(userName, password);

        if(userVO != null) {
            String role = userVO.getRole().toString();
            return JwtUtil.createJwt(userName, role, secretKey, expiredMs);
        } else {
            return null;
        }
    }

    // 가입된 회원인지 체크하고 유저객체 반환
    public UserVO getUser(String userName, String password) {
        UserVO userVO = null;
        userVO = authDao.loginCheck(userName, password);
        return userVO;
    }
}
