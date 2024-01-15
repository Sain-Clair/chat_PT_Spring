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

        if (userVO != null) {
            String role = userVO.getRole().toString();
            return JwtUtil.createJwt(userName, role, expiredMs);
        } else {
            return null;
        }
    }

    // 카카오 로그인
    // 이메일을 통해서 토큰 발급 시키기
    public String loginWithEmail(String email) {
        UserVO userVO = getUserWithEmail(email);

        String role = userVO.getRole().toString();
        String userName = userVO.getId();
        return JwtUtil.createJwt(userName, role, expiredMs);

    }

    // 가입된 회원인지 체크하고 유저객체 반환(로그인을 위해)
    public UserVO getUser(String userName, String password) {
        UserVO userVO = null;
        userVO = authDao.loginCheck(userName, password);
        return userVO;
    }


    // 카카오 로그인을 위해 이메일로 유저객체 반환
    private UserVO getUserWithEmail(String email) {
        return authDao.loginCheckWithEmail(email);
    }

    // 가입한 회원인지 이메일로 확인하고 아이디 반환
    public String checkEmailReturnId(String email) {
        return authDao.checkEmailReturnId(email);
    }

    // 아이디 찾기
    public String findId(String name, String email) {
        return authDao.findId(name, email);
    }

    // 비밀번호 변경을 위한 가입 확인 및 유저 객체 반환
    public UserVO userCheck(String id, String name, String email) {
        UserVO userVO = null;
        userVO = authDao.userCheck(id, name, email);
        return userVO;
    }

    // 비밀번호 변경
    public void changePassword(String id, String newPassword) {
        authDao.changePassword(id, newPassword);
    }


}
