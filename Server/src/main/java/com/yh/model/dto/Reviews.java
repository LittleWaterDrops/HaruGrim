package com.yh.model.dto;

import java.util.Date;

public class Reviews {
	private long id;
	private long userId; // 사용자 id(Users id와 연결)
	private String title; // 회고 제목
	private String content; // 회고 내용
	private String imageUrls;
	private Date createdAt; // 생성 시각
	private Date updatedAt; // 수정 시각
	private Date deletedAt; // 삭제 시각

	public Reviews() {

	}

	public Reviews(long id, long userId, String title, String content, String imageUrls, Date createdAt, Date updatedAt,
			Date deletedAt) {
		super();
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.imageUrls = imageUrls;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(String imageUrls) {
		this.imageUrls = imageUrls;
	}

	@Override
	public String toString() {
		return "Reviews [id=" + id + ", userId=" + userId + ", title=" + title + ", content=" + content + ", imageUrls="
				+ imageUrls + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt
				+ "]";
	}

}
