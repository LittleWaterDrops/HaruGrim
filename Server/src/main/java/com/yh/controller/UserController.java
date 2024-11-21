package com.yh.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.yh.model.dto.User;
import com.yh.model.service.UserService;
import com.yh.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;
	private final JwtUtil jwtUtil;

	public UserController(UserService userService, JwtUtil jwtUtil) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}

	private String extractToken(String header) {
		if (header != null && header.startsWith("Bearer ")) {
			return header.substring(7);
		}
		throw new IllegalArgumentException("Invalid Authorization header");
	}

	@Operation(summary = "마이페이지", description = "로그인된 유저의 정보를 불러오는 API입니다.")
	@GetMapping("/my")
	public ResponseEntity<User> getProfile(@RequestHeader(value = "Authorization", required = true) String token) {
		System.out.println("Authorization Header: " + token); // 디버깅 로그
		if (token == null || token.isEmpty()) {
			return ResponseEntity.status(400).body(null); // Bad Request
		}
		try {
			String extractedToken = extractToken(token);
			System.out.println("Extracted Token: " + extractedToken); // 디버깅 로그
			Long userId = jwtUtil.validateAccessToken(extractedToken);
			System.out.println("Validated UserId: " + userId); // 디버깅 로그
			User user = userService.getProfile(userId);
			return ResponseEntity.ok(user);
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid Token Format: " + e.getMessage()); // 디버깅 로그
			return ResponseEntity.status(400).body(null); // Bad Request
		} catch (IllegalStateException e) {
			System.out.println("Token Validation Failed: " + e.getMessage()); // 디버깅 로그
			return ResponseEntity.status(401).body(null); // Unauthorized
		}
	}

	@Operation(summary = "회원정보 수정", description = "로그인된 유저의 회원정보를 수정하는 API입니다.")
	@PatchMapping("/my")
	public ResponseEntity<User> updateProfile(@RequestBody User user,
			@RequestHeader(value = "Authorization", required = true) String token) {
		try {
			Long userId = jwtUtil.validateAccessToken(extractToken(token));

			user.setId(userId);

			User updatedUser = userService.updateProfile(user);

			return ResponseEntity.ok(updatedUser);
		} catch (IllegalArgumentException e) {
			// Invalid or missing token
			return ResponseEntity.status(400).body(null); // Bad Request
		} catch (IllegalStateException e) {
			// Invalid or expired JWT
			return ResponseEntity.status(401).body(null); // Unauthorized
		}
	}
	
//	@Operation(summary = "마이페이지", description = "로그인된 유저의 정보를 불러오는 API입니다.")
//	@GetMapping("/my")
//	public ResponseEntity<User> getProfile(@RequestParam("userId") Long userId) {
//	    try {
//	        // JWT 검증 없이 바로 userId를 이용
//	        User user = userService.getProfile(userId); // 사용자 정보 조회
//	        return ResponseEntity.ok(user);
//	    } catch (Exception e) {
//	        return ResponseEntity.status(500).body(null); // 서버 오류 처리
//	    }
//	}
//
//	@Operation(summary = "회원정보 수정", description = "로그인된 유저의 회원정보를 수정하는 API입니다.")
//	@PatchMapping("/my")
//	public ResponseEntity<User> updateProfile(@RequestBody User user) {
//	    try {
//	        // JWT 검증 없이 바로 업데이트
//	        User updatedUser = userService.updateProfile(user); // 사용자 정보 수정
//	        return ResponseEntity.ok(updatedUser);
//	    } catch (Exception e) {
//	        return ResponseEntity.status(500).body(null); // 서버 오류 처리
//	    }
//	}

}
