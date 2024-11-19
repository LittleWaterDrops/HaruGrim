package com.yh.model.service;

import java.util.List;

import com.yh.model.dto.Reviews;

public interface ReviewsService {
	Reviews getReviewById(long id);

	List<Reviews> getReviewsByUserId(long userId);

	Reviews createReview(Reviews review);

	Reviews updateReview(Reviews review);

	void deleteReview(long id);
}
