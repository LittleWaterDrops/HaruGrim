package com.yh.model.dto;

import java.util.Date;

public class EmailVerifications {
	private long id;
	private long userId; // 사용자 id(Users id와 연결)
	private String verificationCode; // 이메일 인증 토큰
	private boolean verified; // 인증 여부
	private Date createdAt; // 생성 시각
	private Date expiresAt; // 만료 시각
	
	public EmailVerifications() {

	}

	public EmailVerifications(long id, long userId, String verificationCode, boolean verified, Date createdAt,
			Date expiresAt) {
		super();
		this.id = id;
		this.userId = userId;
		this.verificationCode = verificationCode;
		this.verified = verified;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
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

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
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

	@Override
	public String toString() {
		return "EmailVarifications [id=" + id + ", userId=" + userId + ", verificationCode=" + verificationCode
				+ ", verified=" + verified + ", createdAt=" + createdAt + ", expiresAt=" + expiresAt + "]";
	}
	
	

}
