package com.yh.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yh.exception.ReviewNotFoundException;
import com.yh.model.dao.ReviewsDao;
import com.yh.model.dto.Reviews;

@Service
public class ReviewsServiceImpl implements ReviewsService {
	
    private final ReviewsDao reviewsDao;

    public ReviewsServiceImpl(ReviewsDao reviewsDao) {
        this.reviewsDao = reviewsDao;
    }

    @Override
    @Transactional
    public Reviews createReview(Reviews review) {
        validateReview(review);
        reviewsDao.createReview(review);
        return review;
    }

    @Override
    public List<Reviews> getReviewListByUserId(long userId) {
        return reviewsDao.findReviewListByUserId(userId);
    }

    @Override
    public Reviews getReviewById(long id) {
        Reviews review = reviewsDao.findReviewById(id);
        if (review == null) {
            throw new ReviewNotFoundException("리뷰를 찾을 수 없습니다. ID: " + id);
        }
        return review;
    }

    @Override
    @Transactional
    public Reviews updateReview(Reviews review) {
        validateReview(review);
        int rows = reviewsDao.updateReview(review);
        if (rows == 0) {
            throw new ReviewNotFoundException("업데이트할 리뷰를 찾을 수 없습니다. ID: " + review.getId());
        }
        return review;
    }

    @Override
    @Transactional
    public void deleteReview(long id) {
        int rows = reviewsDao.deleteReview(id);
        if (rows == 0) {
            throw new ReviewNotFoundException("삭제할 리뷰를 찾을 수 없습니다. ID: " + id);
        }
    }

    private void validateReview(Reviews review) {
        if (review.getTitle() == null || review.getTitle().isBlank()) {
            throw new IllegalArgumentException("리뷰 제목은 비어 있을 수 없습니다.");
        }
        if (review.getContent() == null || review.getContent().isBlank()) {
            throw new IllegalArgumentException("리뷰 내용은 비어 있을 수 없습니다.");
        }
    }
}
