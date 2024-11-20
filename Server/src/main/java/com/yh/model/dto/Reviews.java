package com.yh.model.dto;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Reviews {
	private long id;

	@NotNull(message = "userId Null일 수 없습니다!")
	private long userId; // 사용자 id(Users id와 연결)

	@NotBlank(message = "title Null 일 수 없습니다!")
	private String title; // 회고 제목

	@NotBlank(message = "content Null 일 수 없습니다!")
	private String content; // 회고 내용

	@NotNull(message = "imageUrls Null일 수 없습니다!")
	@NotEmpty(message = "imageUrls 리스트는 비어 있을 수 없습니다!")
	private List<String> imageUrls; // 이미지 url을 리스트로 저장.

	private Date createdAt; // 생성 시각
	private Date updatedAt; // 수정 시각

	// 기본 생성자
	public Reviews() {}

	// 모든 필드를 받는 생성자
	public Reviews(long id, long userId, String title, String content, List<String> imageUrls, Date createdAt, Date updatedAt) {
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.imageUrls = imageUrls;
		this.createdAt = createdAt;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
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

	@Override
	public String toString() {
		return "Reviews [id=" + id + ", userId=" + userId + ", title=" + title + ", content=" + content + ", imageUrls="
				+ imageUrls + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
