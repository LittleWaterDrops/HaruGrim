package com.yh.model.service;

import com.yh.model.dto.Auth;

import jakarta.servlet.http.HttpSession;

public interface AuthService {
	void signup(Auth auth);

	Auth login(Auth auth, HttpSession session);

	void logout(HttpSession session);

	void deleteAccount(HttpSession session);
}
