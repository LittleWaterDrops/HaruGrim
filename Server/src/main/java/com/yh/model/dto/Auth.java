package com.yh.model.dto;

public class Auth {
    private Long userId;       // 사용자 ID
    private String username;   // 사용자 이름
    private String password;   // 비밀번호
    private String email;      // 이메일 (회원가입 시 필요)

    // 기본 생성자
    public Auth() {}

    // 모든 필드를 포함한 생성자
    public Auth(Long userId, String username, String password, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getter and Setter
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
