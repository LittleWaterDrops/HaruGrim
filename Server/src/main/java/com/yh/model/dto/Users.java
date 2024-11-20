package com.yh.model.dto;

import java.util.Date;

public class Users {
	private long id;
	private String username; // 사용자명
	private String email; // 이메일
	private String passwordHash; // 비밀번호(해시처리)
	private boolean emailVerified; // 이메일 인증 여부
	private String socialProvider; // 소셜 로그인 제공자(구글, 카카오)
	private String socialId; // 소셜 로그인 id
	private Date createdAt; // 생성 시간
	private Date updatedAt; // 수정 시간
	private Date deletedAt; // 삭제 시간

	public Users(long id, String username, String email, String passwordhash, boolean emailVerified,
			String socialProvider, String socialId, Date createdAt, Date updatedAt, Date deletedAt) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.passwordHash = passwordhash;
		this.emailVerified = emailVerified;
		this.socialProvider = socialProvider;
		this.socialId = socialId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public Users() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getSocialProvider() {
		return socialProvider;
	}

	public void setSocialProvider(String socialProvider) {
		this.socialProvider = socialProvider;
	}

	public String getSocialId() {
		return socialId;
	}

	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", email=" + email + ", passwordHash=" + passwordHash
				+ ", emailVerified=" + emailVerified + ", socialProvider=" + socialProvider + ", socialId=" + socialId
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + "]";
	}

}
