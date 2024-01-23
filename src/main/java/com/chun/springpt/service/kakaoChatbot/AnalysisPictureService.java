package com.chun.springpt.service.kakaoChatbot;

import com.chun.springpt.dao.AuthDao;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
@Slf4j
public class AnalysisPictureService {

    @Async
    @ResponseBody
    public void analysis_picture(String callbackUrl) {

        System.out.println("콜백주소 : " + callbackUrl);

        try {
            // 10000밀리초(10초) 동안 대기합니다.
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // 현재 스레드가 인터럽트(다른 스레드가 중지시키려 할 때)되면 실행됩니다.
            e.printStackTrace();
        }

        Map<String, Object> result = Map.of(
            "version", "2.0",
            "template", Map.of(
                "outputs", new Object[]{
                    Map.of(
                        "simpleText", Map.of(
                            "text", "시간 지난 후 결과 보냄."
                        )
                    )
                }
            )
        );

        // HttpClient 인스턴스 생성
        HttpClient client = HttpClient.newHttpClient();

        // HttpRequest 인스턴스 생성
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(callbackUrl))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(new JSONObject(result).toString()))
            .build();

        try {
            // 요청 보내기 및 응답 받기
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // 응답 상태 코드와 텍스트 출력
            System.out.println("Response status code: " + response.statusCode());
            System.out.println("Response text: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
