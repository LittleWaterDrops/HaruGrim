package com.yh.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yh.model.dto.Reviews;
import com.yh.model.service.ReviewsService;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {

    private final ReviewsService reviewsService;

    public ReviewsController(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }	

    // 회고 생성
    @PostMapping
    public ResponseEntity<Reviews> createReview(@RequestBody Reviews review) {
        Reviews createdReview = reviewsService.createReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    // 회고 리스트 조회
    @GetMapping
    public ResponseEntity<List<Reviews>> getAllReviews() {
        List<Reviews> reviews = reviewsService.getReviewsByUserId(0); // 모든 사용자 조회, 0을 기본값으로 설정 (필요시 수정)
        return ResponseEntity.ok(reviews);
    }

    // 회고 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<Reviews> getReviewById(@PathVariable long id) {
        Reviews review = reviewsService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    // 회고 수정
    @PatchMapping("/{id}")
    public ResponseEntity<Reviews> updateReview(@PathVariable long id, @RequestBody Reviews review) {
        review.setId(id);
        Reviews updatedReview = reviewsService.updateReview(review);
        return ResponseEntity.ok(updatedReview);
    }

    // 회고 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable long id) {
        reviewsService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    // 회고를 이미지로 변환
    @PostMapping("/{id}/text-to-image")
    public ResponseEntity<String> convertReviewToImage(@PathVariable long id) {
        // 여기서는 이미지 생성 서비스를 호출해야 함. (이부분은 나중에 구현)
        String imageUrl = "dummyImageUrl"; // 예시 URL
        return ResponseEntity.ok(imageUrl);
    }
}
