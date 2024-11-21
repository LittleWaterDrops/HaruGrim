package com.yh.model.dto;

public class TokenRequest {
    private String token; // 클라이언트에서 보낼 Refresh Token

    // 기본 생성자
    public TokenRequest() {}

    // 필드를 포함한 생성자
    public TokenRequest(String token) {
        this.token = token;
    }

    // Getter and Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenRequest [token=" + token + "]";
    }
}
