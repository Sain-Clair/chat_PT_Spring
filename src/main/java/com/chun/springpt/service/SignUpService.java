package com.chun.springpt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chun.springpt.dao.FoodDao;
import com.chun.springpt.dao.SignUpDao;
import com.chun.springpt.vo.FoodVO;

@Service
public class SignUpService {
    @Autowired
    private SignUpDao sdao;
    @Autowired
    private FoodDao fdao;
    @Autowired
    private S3uploadService ssservice;

    // 일반 회원가입
    public int insertMembers(Map<String, Object> data) {
        try {
            int insertMemResult = sdao.insertMembers(data);
            int insertNormalResult = sdao.insertNormal(data);
            int nnum = (Integer) data.get("nnum");
            data.put("nnum", nnum); // nnum 값을 data에 삽입
            Map<String, Object> mainimagename = (Map<String, Object>) data.get("nm_profileimg");
            System.out.println("메인이미지" + mainimagename);
            int insertMemFoodResult = sdao.insertMemFood(data);
            int sum = insertMemResult + insertNormalResult + insertMemFoodResult;
            if (sum > 3) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // PT 회원가입
    // @Transactional
    public int insertTrainerMembers(Map<String, Object> data) {
        try {
            List<Map<String, String>> awards = (List<Map<String, String>>) data.get("awards");
            // award 처리
            for (int i = 0; i < awards.size(); i++) {
                data.put("awards" + (i + 1), awards.get(i).get("name"));
            }
            for (int i = awards.size(); i < 5; i++) {
                data.put("awards" + (i + 1), "");
            }

            int insertMemResult = sdao.insertMembers(data);
            int insertTrainerResult = sdao.insertTrainer(data);
            int tnum = (Integer) data.get("tnum");
            data.put("tnum", tnum); //
            int insertPTimage = sdao.updatePTimage(data);
            int sum = insertMemResult + insertTrainerResult + insertPTimage;
            System.out.println("트레이너 회원가입 sum:" + sum);
            if (sum >= 3) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
