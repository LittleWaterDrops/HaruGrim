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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {

    private final ReviewsService reviewsService;

    public ReviewsController(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }	

    @Operation(summary = "회고 생성", 
               description = "새로운 회고를 생성합니다.",
               requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                   description = "생성할 회고 데이터",
                   required = true,
                   content = @Content(schema = @Schema(implementation = Reviews.class))
               ))
    @PostMapping
    public ResponseEntity<Reviews> createReview(@Valid @RequestBody Reviews review) {
        Reviews createdReview = reviewsService.createReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @Operation(summary = "회고 리스트 조회", 
               description = "사용자 ID로 모든 회고를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<Reviews>> getReviewListByUserId() {
        List<Reviews> reviews = reviewsService.getReviewListByUserId(0); // 모든 사용자 조회, 0을 기본값으로 설정 (필요시 수정)
        return ResponseEntity.ok(reviews);
    }

    @Operation(summary = "회고 단건 조회", 
               description = "ID로 특정 회고를 조회합니다.",
               parameters = {
                   @Parameter(name = "id", description = "조회할 회고 ID", required = true)
               })
    @GetMapping("/{id}")
    public ResponseEntity<Reviews> getReviewById(@PathVariable long id) {
        Reviews review = reviewsService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    @Operation(summary = "회고 수정", 
               description = "ID를 기반으로 특정 회고를 수정합니다.",
               parameters = {
                   @Parameter(name = "id", description = "수정할 회고 ID", required = true)
               },
               requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                   description = "수정할 데이터",
                   required = true,
                   content = @Content(schema = @Schema(implementation = Reviews.class))
               ))
    @PatchMapping("/{id}")
    public ResponseEntity<Reviews> updateReview(@PathVariable long id, @RequestBody Reviews review) {
        review.setId(id);
        Reviews updatedReview = reviewsService.updateReview(review);
        return ResponseEntity.ok(updatedReview);
    }

    @Operation(summary = "회고 삭제", 
               description = "ID를 기반으로 특정 회고를 삭제합니다.",
               parameters = {
                   @Parameter(name = "id", description = "삭제할 회고 ID", required = true)
               })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable long id) {
        reviewsService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "회고를 이미지로 변환", 
               description = "특정 회고를 이미지로 변환하고 URL을 반환합니다.",
               parameters = {
                   @Parameter(name = "id", description = "이미지로 변환할 회고 ID", required = true)
               })
    @PostMapping("/{id}/text-to-image")
    public ResponseEntity<String> convertReviewToImage(@PathVariable long id) {
        // 여기서는 이미지 생성 서비스를 호출해야 함. (이부분은 나중에 구현)
        String imageUrl = "dummyImageUrl"; // 예시 URL
        return ResponseEntity.ok(imageUrl);
    }
}
