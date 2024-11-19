package com.yh.model.dao;

import java.util.List;

import com.yh.model.dto.Images;

public interface ImagesDao {
	Images findById(long id);

	List<Images> findByReviewId(long reviewId);

	int saveImage(Images image);

	int deleteImage(long id);
}
