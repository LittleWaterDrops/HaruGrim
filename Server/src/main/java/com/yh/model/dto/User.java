package com.yh.model.dto;

public class User {
    private Long id;           // 사용자 ID
    private String username;   // 사용자 이름
    private String email;      // 사용자 이메일

    // 기본 생성자
    public User() {}

    // 모든 필드를 포함한 생성자
    public User(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
