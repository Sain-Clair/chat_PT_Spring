package com.chun.springpt.controller;

import com.chun.springpt.domain.dto.KakaoProfile;
import com.chun.springpt.service.AuthService;
import com.chun.springpt.service.KakaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/kakao")
@Slf4j
public class KakaoController {

    @Autowired
    private AuthService authService;

    @Autowired
    private KakaoService kakaoService;

    @GetMapping("/callback")
    public ResponseEntity<Map<String, String>> kakaoCallback(String code) {

        // 프론트에서 받은 코드를 통해 카카오 서버로 토큰을 요청
        ResponseEntity<String> tokenResponse = kakaoService.getKakaoToken(code);

        // 토큰을 보내서 유저의 정보를 가져옴
        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(tokenResponse);

        // 유저의 정보를 통해 이메일을 가져옴
        String kakaoEmail = kakaoService.getKakaoEmail(kakaoProfile);

        // 이메일을 통해 회원가입 여부를 확인
        int isSign = authService.checkEmail(kakaoEmail);

        Map<String, String> responseData = new HashMap<>();
        if (isSign == 0) {
            // isSign : false
            // UUID로 만든 아이디,이메일,닉네임,프로필 사진 넘기기
            // 그리고 뷰에서 그걸 로컬 스토리지에 올려서 리디렉션
            // 바인딩 받아서 그대로 회원가입 진행하기
            responseData.put("isSign", "false");
            responseData.put("id", "kakao_"+kakaoEmail);
            responseData.put("email", kakaoEmail);
            responseData.put("nickname", kakaoService.getKakaoNickname(kakaoProfile));
            responseData.put("profileImage", kakaoService.getKakaoImage(kakaoProfile));

        } else {
            // isSign : true
            // 이메일에 맞는 토큰 생성해서 보내야됨
            // 그리고 뷰에서 그걸 로컬 스토리지에 올려서 리디렉션
            // 바인딩 받아서 그대로 로그인 진행하기
            String token = authService.loginWithEmail(kakaoEmail);
            responseData.put("isSign", "true");
            responseData.put("token", token);

        }
        return ResponseEntity.ok(responseData);

    }
}
