package com.yh.model.service;

import java.util.List;

import com.yh.model.dto.Images;

public interface ImagesService {
	Images getImageById(long id);

	List<Images> getImagesByReviewId(long reviewId);

	Images saveImage(Images image);

	void deleteImage(long id);
}
