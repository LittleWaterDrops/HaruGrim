package com.yh.model.service;

import java.util.List;

import com.yh.model.dto.Reviews;

public interface ReviewsService {
	
	Reviews createReview(Reviews review);

	List<Reviews> getReviewListByUserId(long userId);

	Reviews getReviewById(long id);

	Reviews updateReview(Reviews review);

	void deleteReview(long id);
	
	String convertReviewToImage(long reviewId);
}
