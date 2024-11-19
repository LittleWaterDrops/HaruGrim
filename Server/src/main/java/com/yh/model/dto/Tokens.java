package com.yh.model.dto;

import java.util.Date;

public class Tokens {
    private long id;
    private long userId; // 사용자 id(Users id와 연결)
    private String tokenType; // 토큰 유형 (ACCESS 또는 REFRESH)
    private String token; // 토큰 값
    private Date createdAt; // 생성 시각
    private Date expiresAt; // 만료 시각
    private Date updatedAt; // 수정 시각 (업데이트가 필요한 경우)
	
	public Tokens() {
		
	}

	public Tokens(long id, long userId, String tokenType, String token, Date createdAt, Date expiresAt,
			Date updatedAt) {
		super();
		this.id = id;
		this.userId = userId;
		this.tokenType = tokenType;
		this.token = token;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
		this.updatedAt = updatedAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
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

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Tokens [id=" + id + ", userId=" + userId + ", tokenType=" + tokenType + ", token=" + token
				+ ", createdAt=" + createdAt + ", expiresAt=" + expiresAt + ", updatedAt=" + updatedAt + "]";
	}

	
}
