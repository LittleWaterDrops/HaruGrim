package com.yh.model.dao;

import java.util.Date;
import java.util.List;

import com.yh.model.dto.Reviews;

public interface ReviewsDao {
	Reviews findById(long id);

	List<Reviews> findByUserId(long userId);

	int saveReview(Reviews review);

	int updateReview(Reviews review);

	int deleteReview(long id, Date deletedAt);
}
