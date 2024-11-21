package com.yh.model.dto;

import java.util.Date;

public class Tokens {
    private Long id;
    private Long userId;
    private String tokenType; // 'ACCESS' or 'REFRESH'
    private String token;
    private Date createdAt;
    private Date expiresAt;

    // 기본 생성자
    public Tokens() {}

    // 모든 필드를 포함한 생성자
    public Tokens(Long id, Long userId, String tokenType, String token, Date createdAt, Date expiresAt) {
        this.id = id;
        this.userId = userId;
        this.tokenType = tokenType;
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }
}
