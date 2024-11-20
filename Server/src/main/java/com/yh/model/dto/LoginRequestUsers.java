package com.yh.model.dto;

public class LoginRequestUsers {
	private String username;
	private String passwordHash;

	// 기본 생성자, getter, setter
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

}
