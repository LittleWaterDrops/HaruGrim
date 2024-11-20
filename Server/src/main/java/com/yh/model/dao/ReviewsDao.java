package com.yh.model.dao;

import java.util.List;

import com.yh.model.dto.Reviews;

public interface ReviewsDao {
	
	int createReview(Reviews review);

	List<Reviews> findReviewListByUserId(long userId);

	Reviews findReviewById(long id);
	
	int updateReview(Reviews review);

	int deleteReview(long id);
}
