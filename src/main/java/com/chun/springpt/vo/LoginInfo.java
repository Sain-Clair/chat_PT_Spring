package com.chun.springpt.vo;

import lombok.Builder;

public class LoginInfo {
	private String name;
	private String token;

	@Builder
	public LoginInfo(String name, String token) {
		this.name = name;
		this.token = token;
	}
}