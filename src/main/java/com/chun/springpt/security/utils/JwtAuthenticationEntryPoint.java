package com.chun.springpt.security.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// Spring Security에서 인증에 실패했을 때 처리하는 클래스이고
// 추상클래스인 BasicAuthenticationEntryPoint를 상속받아 재정의해서 사용한다.
// BasicAuthenticationEntryPoint는 기본적인 HTTP 기반 인증 실패 처리를 구현한 클래스다. 
@Component
public class JwtAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, IOException {

    // 인증에 실패했을 때 401 Unauthorized 에러를 리턴
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    // 마임 타입을 json으로 설정
    response.setContentType("application/json");

    // 에러 메시지를 출력
    response.getWriter().write("{ \"message\": \"" + authException.getMessage() + "\" }");
  }

  // Bean의 속성이 설정된 후에 호출
  @Override
  public void afterPropertiesSet() {
    // setRealmName()을 호출하여 인증 영역의 이름을 "JWT Authentication"으로 설정
    setRealmName("JWT Authentication");
    super.afterPropertiesSet();
  }
}