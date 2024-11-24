package com.yh.model.service;

import com.yh.model.dto.Auth;
import com.yh.model.dto.TokenResponse;

public interface AuthService {
    void signup(Auth auth); // 회원가입

    TokenResponse login(Auth auth); //로그인

    TokenResponse refreshToken(String refreshToken); // access토큰 재발급(Refresh 토큰 있을시)

    void logout(String accessToken); // Access Token 기반 로그아웃

    void deleteAccount(String accessToken); // Access Token 기반 회원탈퇴

}
