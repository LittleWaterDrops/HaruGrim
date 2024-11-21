package com.yh.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yh.exception.ReviewNotFoundException;
import com.yh.model.dao.ReviewsDao;
import com.yh.model.dto.Reviews;
import com.yh.util.OpenAIUtil;

@Service
public class ReviewsServiceImpl implements ReviewsService {
	
    private final ReviewsDao reviewsDao;
    
    private final OpenAIUtil openAIUtil;

    public ReviewsServiceImpl(ReviewsDao reviewsDao, OpenAIUtil openAIUtil) {
        this.reviewsDao = reviewsDao;
        this.openAIUtil = openAIUtil;
    }

    @Override
    @Transactional
    public Reviews createReview(Reviews review) {
        validateReview(review);
        
        String imageUrl;
        // OpenAI API 호출
        try {
            imageUrl = openAIUtil.generateImage(review.getContent());
            review.setImageUrls(List.of(imageUrl));
        } catch (Exception e) {
            throw new RuntimeException("이미지 생성 중 오류 발생: " + e.getMessage());
        }
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
    
    @Override
    @Transactional
    public String convertReviewToImage(long reviewId) {
        Reviews review = getReviewById(reviewId); // 리뷰 정보 조회
        String imageUrl = openAIUtil.generateImageFromReview(review.getTitle(), review.getContent());
        reviewsDao.updateImageUrls(reviewId, imageUrl); // DB에 URL 업데이트
        return imageUrl;
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
