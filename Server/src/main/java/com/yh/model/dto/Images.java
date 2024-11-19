package com.yh.model.dto;

import java.util.Date;

public class Images {
	private long id;
	private long reviewId; // 회고 id(Reviews id와 연결)
	private String imageUrl; // 이미지 url
	private Date createdAt; // 이미지 생성 시각
	private Date updatedAt; // 이미지 수정 시각
	
	public Images() {
		
	}

	public Images(long id, long reviewId, String imageUrl, Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.reviewId = reviewId;
		this.imageUrl = imageUrl;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getReviewId() {
		return reviewId;
	}

	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
		return "Images [id=" + id + ", reviewId=" + reviewId + ", imageUrl=" + imageUrl + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}
	
	

}
