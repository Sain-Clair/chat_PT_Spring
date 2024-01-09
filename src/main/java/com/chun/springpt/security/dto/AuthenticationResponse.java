package com.chun.springpt.security.dto;

// 모든 필드를 인자로 받는 생성자를 자동으로 생성
import lombok.AllArgsConstructor;
import lombok.Getter;
// 인자 없이 객체를 생성할 수 있는 기본 생성자를 자동으로 생성
import lombok.NoArgsConstructor;
import lombok.Setter;

//사용자 인증 후 반환되는 응답 데이터, 특히 접근 토큰을 캡슐화하기 위해 사용
/*
 *인증 과정: 사용자가 로그인 정보(아이디와 비밀번호)를 제공하면, 서버는 이를 검증하고 JWT 토큰을 생성
응답 생성: 생성된 JWT 토큰은 AuthenticationResponse 객체의 accessToken 필드에 저장
클라이언트에 반환: 이 객체는 클라이언트(예: 웹 브라우저, 모바일 앱)에 JSON 형태로 반환 
클라이언트는 이 토큰을 사용하여 이후의 요청에서 인증을 증명한다.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
  // 사용자 인증 후 생성된 접근 토큰을 저장
  private String accessToken;
}