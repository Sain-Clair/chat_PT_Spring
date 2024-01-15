package com.chun.springpt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chun.springpt.dao.SignUpDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SignUpService {
    @Autowired
    private SignUpDao dao;

    // 이미 존재하는 이메일인지 확인
    public int validCheckEmail(String email){
        // 0이면 가입가능, 1이면 불가능
        int checkEmail = dao.validCheckEmail(email);
        return checkEmail;
    }

    public int validCheckId(String id) {
        int checkId = dao.validCheckId(id);
        return checkId;
    }
}
