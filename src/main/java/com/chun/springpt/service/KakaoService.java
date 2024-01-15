package com.chun.springpt.service;

import com.chun.springpt.domain.dto.KakaoProfile;
import com.chun.springpt.domain.dto.OAuthToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class KakaoService {


    // 프론트에서 받은 코드를 통해 카카오 서버로 토큰을 요청
    public ResponseEntity<String> getKakaoToken(String code) {
        // POST 방식으로 key=value 데이터를 요청 (카카오쪽으로)
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "aa7e1b658afaea7d32248761c5aed3ef");
        params.add("redirect_uri", "http://localhost/springpt/kakao/callback");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        return response;
    }
    
    // 토큰을 보내서 유저의 정보를 가져옴
    public KakaoProfile getKakaoProfile(ResponseEntity<String> response) {
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        log.info("카카오 엑세스 토큰 : " + oAuthToken.getAccess_token());

        // POST 방식으로 key=value 데이터를 요청 (카카오쪽으로)
        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        // Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return kakaoProfile;
    }

    // 유저의 정보에서 카카오 아이디(시퀀스)를 가져옴
    public String getKakaoId(KakaoProfile kakaoProfile) {
        return kakaoProfile.getId().toString();
    }

    // 유저의 정보에서 카카오 이메일을 가져옴
    public String getKakaoEmail(KakaoProfile kakaoProfile) {
        return kakaoProfile.getKakao_account().getEmail();
    }

    // 유저의 정보에서 카카오 닉네임을 가져옴
    public String getKakaoNickname(KakaoProfile kakaoProfile) {
        return kakaoProfile.getKakao_account().getProfile().getNickname();
    }

    // 유저의 정보에서 카카오 이미지를 가져옴
    public String getKakaoImage(KakaoProfile kakaoProfile) {
        return kakaoProfile.getKakao_account().getProfile().getProfile_image_url();
    }
}
