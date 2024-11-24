package com.yh.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yh.model.dto.Reviews;
import com.yh.model.service.ReviewsService;
import com.yh.util.JwtUtil;
import com.yh.util.OpenAIUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "*") // 클라이언트 URL 허용
public class ReviewsController {

	private final ReviewsService reviewsService;
	private final JwtUtil jwtUtil;
	private final OpenAIUtil openAIUtil;

	public ReviewsController(ReviewsService reviewsService, JwtUtil jwtUtil, OpenAIUtil openAIUtil) {
		this.reviewsService = reviewsService;
		this.jwtUtil = jwtUtil;
		this.openAIUtil = openAIUtil;
	}

	// JWT에서 "Bearer" 제거하는 유틸리티 메서드
	private String extractToken(String header) {
		if (header != null && header.startsWith("Bearer ")) {
			return header.substring(7);
		}
		throw new IllegalArgumentException("Invalid Authorization header");
	}

	@Operation(summary = "회고 생성", description = "새로운 회고를 생성합니다.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "생성할 회고 데이터", required = true, content = @Content(schema = @Schema(implementation = Reviews.class))))
	@PostMapping
    public ResponseEntity<Reviews> createReview(@RequestBody Reviews review,
                                                 @RequestHeader("Authorization") String token) {
        try {
            Long userId = jwtUtil.validateAccessToken(extractToken(token)); // JWT에서 user_id 추출
            review.setUserId(userId); // review에 user_id 설정

            // 회고 생성
            Reviews createdReview = reviewsService.createReview(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
    }

    @Operation(summary = "회고를 이미지로 변환", 
               description = "특정 회고를 이미지로 변환하고 URL을 반환합니다.", 
               parameters = {@Parameter(name = "id", description = "이미지로 변환할 회고 ID", required = true)})
    @PostMapping("/{id}/text-to-image")
    public ResponseEntity<String> convertReviewToImage(@PathVariable long id, @RequestBody Reviews review,
                                                       @RequestHeader("Authorization") String token) {
        try {
            Long userId = jwtUtil.validateAccessToken(extractToken(token)); // JWT 검증

            // 이미지 생성
            String imageUrl = openAIUtil.generateImageFromReview(review.getTitle(), review.getContent());
            System.out.println("Generated Image URL: " + imageUrl);

            // 이미지 URL 반환
            return ResponseEntity.ok(imageUrl);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(401).build(); // Unauthorized
        } catch (Exception e) {
            return ResponseEntity.status(500).body("이미지 생성 중 오류 발생: " + e.getMessage());
        }
    }
    
    @Operation(summary = "회고를 이미지로 변환(여러개)", 
            description = "특정 회고를 이미지로 변환하고 URL을 반환합니다.", 
            parameters = {@Parameter(name = "id", description = "이미지로 변환할 회고 ID", required = true)})
    @PostMapping("/text-to-image")
    public ResponseEntity<List<String>> generateMultipleImages(@RequestBody Reviews review,
                                                               @RequestHeader("Authorization") String token) {
        try {
            Long userId = jwtUtil.validateAccessToken(extractToken(token)); // JWT 검증

            // OpenAI를 사용하여 이미지 3개 생성
            List<String> imageUrls = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                String imageUrl = openAIUtil.generateImageFromReview(review.getTitle(), review.getContent());
                imageUrls.add(imageUrl);
            }

            return ResponseEntity.ok(imageUrls);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonList("이미지 생성 중 오류 발생: " + e.getMessage()));
        }
    }
    
//    @Operation(summary = "모든 리뷰 가져오기", description = "현재 사용자의 모든 리뷰를 반환합니다.")
//    @GetMapping
//    public ResponseEntity<List<Reviews>> getAllReviews(@RequestHeader("Authorization") String token) {
//        try {
//            Long userId = jwtUtil.validateAccessToken(extractToken(token));
//            List<Reviews> reviews = reviewsService.getReviewListByUserId(userId);
//            return ResponseEntity.ok(reviews);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//    }


	@Operation(summary = "회고 리스트 조회", description = "사용자 ID로 모든 회고를 조회합니다.")
	@GetMapping
	public ResponseEntity<List<Reviews>> getReviewListByUserId(@RequestHeader("Authorization") String token) {
		try {
			Long userId = jwtUtil.validateAccessToken(extractToken(token)); // JWT에서 user_id 추출
			List<Reviews> reviews = reviewsService.getReviewListByUserId(userId);
			return ResponseEntity.ok(reviews);
		} catch (IllegalArgumentException | IllegalStateException e) {
			return ResponseEntity.status(401).build(); // Unauthorized
		}
	}

	@Operation(summary = "회고 단건 조회", description = "ID로 특정 회고를 조회합니다.", parameters = {
			@Parameter(name = "id", description = "조회할 회고 ID", required = true) })
	@GetMapping("/{id}")
	public ResponseEntity<Reviews> getReviewById(@PathVariable long id) {
		Reviews review = reviewsService.getReviewById(id);
		return ResponseEntity.ok(review);
	}

	@Operation(summary = "회고 수정", description = "ID를 기반으로 특정 회고를 수정합니다.", parameters = {
			@Parameter(name = "id", description = "수정할 회고 ID", required = true) }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "수정할 데이터", required = true, content = @Content(schema = @Schema(implementation = Reviews.class))))
	@PatchMapping("/{id}")
	public ResponseEntity<Reviews> updateReview(@PathVariable long id, @RequestBody Reviews review) {
		review.setId(id);
		Reviews updatedReview = reviewsService.updateReview(review);
		return ResponseEntity.ok(updatedReview);
	}

	@Operation(summary = "회고 삭제", description = "ID를 기반으로 특정 회고를 삭제합니다.", parameters = {
			@Parameter(name = "id", description = "삭제할 회고 ID", required = true) })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteReview(@PathVariable long id) {
		reviewsService.deleteReview(id);
		return ResponseEntity.noContent().build();
	}

}
