package com.yh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

	// 유효성 검사 실패시 발생하는 예외를 처리
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		// 유효성 검사 실패 결과를 바탕으로 필드 에러 메시지를 수집
		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();

		// 필드 에러 메시지들을 한 줄로 합침
		String errorMessages = fieldErrors.stream().map(error -> error.getField() + ": " + error.getDefaultMessage())
				.collect(Collectors.joining(", "));

		// 400 Bad Request 응답 반환
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed: " + errorMessages);
	}

	// 데이터베이스 제약 조건 위반시 발생하는 예외를 처리
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
		// DB 제약 조건 위반 시, 400 Bad Request 응답 반환
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("데이터 제약 조건 위반: " + ex.getMessage());
	}

	// 리뷰 예외 처리
	@ExceptionHandler(ReviewNotFoundException.class)
	public ResponseEntity<String> handleReviewNotFoundException(ReviewNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	// 다른 모든 예외를 처리 (예: NullPointerException, IllegalArgumentException 등)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception ex) {
		// 예기치 않은 예외 처리
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류: " + ex.getMessage());
	}

}
