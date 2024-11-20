package com.yh.model.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yh.model.dao.ReviewsDao;
import com.yh.model.dto.Reviews;

@Service
public class ReviewsServiceImpl implements ReviewsService {
	
	private ReviewsDao reviewsDao;

	public ReviewsServiceImpl(ReviewsDao reviewsDao) {
		this.reviewsDao = reviewsDao;
	}

	@Override
	public Reviews createReview(Reviews review) {
		reviewsDao.createReview(review); // 회고 저장
		return review;
	}

	@Override
	public Reviews getReviewById(long id) {
		return reviewsDao.findById(id);
	}

	@Override
	public List<Reviews> getReviewsByUserId(long userId) {
		return reviewsDao.findByUserId(userId);
	}

	@Override
	public Reviews updateReview(Reviews review) {
		reviewsDao.updateReview(review); // 회고 업데이트
		return review;
	}

	@Override
	public void deleteReview(long id) {
		Date deletedAt = new Date();
		reviewsDao.deleteReview(id, deletedAt); // 회고 삭제
	}
}
