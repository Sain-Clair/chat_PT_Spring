package com.chun.springpt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chun.springpt.service.SignUpService;
import com.chun.springpt.vo.FoodVO;

@RestController
public class SignUpController {
    @Autowired
    private SignUpService signUpService;

    // id 중복 체크
    @PostMapping("/signUp/id")
    public int checkId(@RequestBody Map<String, String> data) {
        String id = data.get("id");
        int num = signUpService.validCheckId(id);
        if (num < 1) {
            return 0;
        } else {
            return 1;
        }
    }

    // 이메일 중복 체크
    @PostMapping("/signUp/email")
    public int emailCheck(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        int num = signUpService.validCheckEmail(email);
        if (num < 1) {
            return 0;
        } else {
            return 1;
        }
    }

    // 음식 리스트 가져오기
    @GetMapping("/signUp/getfoodList")
    public List<Map<String, String>> getfoodList() {
        List<FoodVO> foodList = signUpService.selectFoodList();
        List<Map<String, String>> resultMap = new ArrayList<>();
        for (FoodVO food : foodList) {
            Map<String, String> foodMap = new HashMap<>();
            foodMap.put("FOODNUM", String.valueOf(food.getFOODNUM()));
            foodMap.put("FOODIMG", food.getFOODIMG());
            foodMap.put("FOODNAME", food.getFOODNAME());
            resultMap.add(foodMap);
        }
        return resultMap;
    }
}
