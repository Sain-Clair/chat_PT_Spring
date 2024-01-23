package com.chun.springpt.service;

import com.chun.springpt.dao.AuthDao;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KakaoChatbotService {

    public final String s3ImageUrl = "https://chat-pt.s3.ap-northeast-2.amazonaws.com";

    @Autowired
    private AuthDao authDao;

    // 카카오톡 채널 연동 코드를 가져오는 메소드
    public String getPlusfriendUserKey(String body) {
        JSONObject loadJson = new JSONObject(body);
        String plusfriendUserKey = loadJson.optJSONObject("userRequest").optJSONObject("user").optJSONObject("properties").optString("plusfriend_user_key");
        return plusfriendUserKey;
    }

    // 채널 연동코드로 회원의 닉네임을 가져오는 메소드
    // 연동이 안되어 있거나 일반회원이 아니면 null 반환
    public String getNicknameByPlusfriendUserKey(String plusfriendUserKey) {
        return authDao.getNicknameByPlusfriendUserKey(plusfriendUserKey);
    }

    // 채널 연동코드로 회원의 ID을 가져오는 메소드
    // 연동이 안되어 있거나 일반회원이 아니면 null 반환
    public String getUserNameByPlusfriendUserKey(String plusfriendUserKey) {
        return authDao.getUserNameByPlusfriendUserKey(plusfriendUserKey);
    }
}
