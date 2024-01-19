package com.chun.springpt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chun.springpt.dao.FoodDao;
import com.chun.springpt.dao.SignUpDao;
import com.chun.springpt.vo.FoodVO;

@Service
public class SignUpService {
    @Autowired
    private SignUpDao sdao;
    @Autowired
    private FoodDao fdao;

    // 이미 존재하는 이메일인지 확인
    public int validCheckEmail(String email) {
        // 0이면 가입가능, 1이면 불가능
        int checkEmail = sdao.validCheckEmail(email);
        System.out.println("서비스" + checkEmail);
        return checkEmail;
    }

    public int validCheckId(String id) {
        int checkId = sdao.validCheckId(id);
        return checkId;
    }

    // 음식 리스트 가져오기
    public List<FoodVO> selectFoodList() {
        return fdao.selectFoodList();
    }
}
