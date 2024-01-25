package com.chun.springpt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chun.springpt.service.SignUpService;
import com.chun.springpt.vo.FoodVO;

@RestController
public class SignUpController {
    @Autowired
    private SignUpService signUpService;

    // 일반 회원 가입
    @PostMapping("/signUp/completeSignUp")
    public int completeSignUp(@RequestBody Map<String, Object> data) {
        int result = signUpService.insertMembers(data);
        System.out.println("여기는 회원가입 완료창:" + result);
        System.out.println("Log1====================");
        for (Map.Entry e : data.entrySet()) {
            System.out.println(e.getKey() +":"+e.getValue());
        }

        return result;
    }

    // pt회원 가입
    @PostMapping("/signUp/PTcompleteSignUp")
    public int postMethodName(@RequestBody Map<String, Object> data) {
        // Map의 모든 키-값 쌍을 반복하여 출력
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String valueType = (value == null) ? "null" : value.getClass().getName(); // 값의 타입을 가져옴

            System.out.println("Key: " + key + ", Value Type: " + valueType);
        }

//        for (Map.Entry<String, Object> entry : data.entrySet()) {
//            String key = entry.getKey();
//            Object value = entry.getValue();
//
//            // 값이 String 타입이고 길이가 100자를 초과하는 경우, 문자열의 일부만 또는 길이만 출력
//            if (value instanceof String && ((String) value).length() > 100) {
//                System.out.println("Key: " + key + ", Value: (string length: " + ((String) value).length() + ")");
//                // 또는
//                // System.out.println("Key: " + key + ", Value: " + ((String) value).substring(0, 50) + "...");
//            } else {
//                // 그 외의 경우, 전체 값을 출력
//                System.out.println("Key: " + key + ", Value: " + value);
//            }
//        }
        int result = signUpService.insertTrainerMembers(data);
        System.out.println("트레이너 회원가입 완료창:" + result);
        return result;
    }

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
