package com.chun.springpt.controller;

import com.chun.springpt.domain.dto.LoginRequest;
import com.chun.springpt.service.AuthService;
import com.chun.springpt.service.UserService;
import com.chun.springpt.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	@Autowired
	private HttpServletRequest request;

	private final AuthService authService;

	private final UserService userService;

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest dto) {

		String token = authService.login(dto.getUserName(), dto.getPassword());
		if (token == null) {
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", "로그인 실패");
			return ResponseEntity.badRequest().body(errorResponse);
		}

		String name = "";
		String nickname = "";
		String role = userService.getRole(dto.getUserName());

		if (Objects.equals(role, "TRAINER")) {
			name = userService.getName(dto.getUserName());
		} else if (Objects.equals(role, "NORMAL")) {
			nickname = userService.getNickname(dto.getUserName());
		}

		Map<String, String> responseData = new HashMap<>();
		responseData.put("token", token);
		responseData.put("role", role);
		responseData.put("name", name);
		responseData.put("nickname", nickname);

		return ResponseEntity.ok(responseData);
	}

	@GetMapping("/checkToken")
	public ResponseEntity<?> checkToken() {
		// 헤더에서 토큰 추출
		String authorizationHeader = request.getHeader("Authorization");
		String token = JwtUtil.extractToken(authorizationHeader);

		if (token == null || token.isEmpty()) {
			return ResponseEntity.badRequest().body("Invalid or empty token");
		}

		try {
			// 사용자 권한
			String userRole = JwtUtil.getRole(token);
			return ResponseEntity.ok().body(userRole);
		} catch (Exception e) {
			log.error("Token processing error", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token processing error");
		}
	}

}
