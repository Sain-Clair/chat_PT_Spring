package com.chun.springpt.controller;

import com.chun.springpt.service.AuthService;
import com.chun.springpt.service.KakaoService;
import com.chun.springpt.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/kakao")
@Slf4j
public class KakaoController {

    @Autowired
    private AuthService authService;

    @Autowired
    private KakaoService kakaoService;

    @Autowired
    private UserService userService;

    @GetMapping("/kakaoLogin/{code}")
    public ResponseEntity<Map<String, String>> kakaoLogin(@PathVariable("code") String code) {
        // 프론트에서 받은 코드를 통해 카카오 서버로 토큰을 요청
        ResponseEntity<String> tokenResponse = kakaoService.getKakaoToken(code);

        // 토큰을 통해 디코딩된 유저 정보 리스트 반환받기
        String decodedIDToken = kakaoService.getDecodedIDToken(tokenResponse);

        // 토큰을 통해 access_token 가져오기
        String accessToken = kakaoService.getAccessoken(tokenResponse);

        // 유저의 정보를 통해 이메일을 가져옴
        String kakaoEmail = kakaoService.getEmail(decodedIDToken);

        // 이메일을 통해 회원가입 여부를 확인
        String id = authService.checkEmailReturnId(kakaoEmail);

        Map<String, String> responseData = new HashMap<>();
        if (id == null) { // 비회원이면 회원가입할 때 채워줄 정보 보내기

            responseData.put("isSign", "false");
            responseData.put("id", "kakao_" + kakaoEmail);
            responseData.put("email", kakaoEmail);
            responseData.put("nickname", kakaoService.getNickname(decodedIDToken));

        } else { // 회원이면 토큰 발급해서 보내기

            String name= "";
            String nickname = "";
            String role = userService.getRole(id);

            if (Objects.equals(role, "TRAINER")) {
                name = userService.getName(id);
            } else if (Objects.equals(role, "NORMAL")) {
                nickname = userService.getNickname(id);
            } else if (Objects.equals(role, "ADMIN")) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "관리자 로그인 이용 필요");
                return ResponseEntity.ok(errorResponse);
            }

            String token = authService.loginWithEmail(kakaoEmail);
            responseData.put("isSign", "true");
            responseData.put("token", token);
            responseData.put("role", role);
            responseData.put("name", name);
            responseData.put("nickname", nickname);
        }

        return ResponseEntity.ok(responseData);
    }

}
