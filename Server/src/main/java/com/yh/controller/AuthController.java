package com.yh.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yh.model.dto.Auth;
import com.yh.model.dto.TokenRequest;
import com.yh.model.dto.TokenResponse;
import com.yh.model.service.AuthService;
import com.yh.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // 클라이언트 URL 허용
public class AuthController {
	private final AuthService authService;
	private final JwtUtil jwtUtill;

	public AuthController(AuthService authService, JwtUtil jwtUtill) {
		this.authService = authService;
		this.jwtUtill = jwtUtill;
	}

	@Operation(summary = "회원가입", description = "회원가입 API입니다.")
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody Auth auth) {
		authService.signup(auth);
		return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
	}

	@Operation(summary = "회원탈퇴", description = "회원탈퇴 API입니다.")
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteAccount(@RequestHeader("Authorization") String accessToken) {
		authService.deleteAccount(accessToken.replace("Bearer ", ""));
		return ResponseEntity.ok("회원 탈퇴 성공");
	}

	@Operation(summary = "로그인", description = "로그인 API입니다.")
	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody Auth auth) {
		TokenResponse tokenResponse = authService.login(auth);
		return ResponseEntity.ok(tokenResponse); // Access, Refresh 토큰 반환
	}


	@Operation(summary = "로그아웃", description = "로그아웃 API입니다.")
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestHeader(value = "Authorization", required = false) String accessToken) {
	    System.out.println("Authorization 헤더 값: " + accessToken); // 헤더 값 출력

	    if (accessToken == null || accessToken.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authorization 헤더가 필요합니다.");
	    }

	    // "Bearer " 접두사를 제거
	    String token = accessToken.replace("Bearer ", "");
	    System.out.println(token);

	    // Access Token 검증 로직
	    authService.logout(token);

	    return ResponseEntity.ok("로그아웃 성공");
	}


	@Operation(summary = "토큰 갱신", description = "Refresh Token을 사용하여 Access Token을 갱신합니다.")
	@PostMapping("/refresh")
	public ResponseEntity<TokenResponse> refreshToken(@RequestBody TokenRequest tokenRequest) {
		TokenResponse tokenResponse = authService.refreshToken(tokenRequest.getToken());
		return ResponseEntity.ok(tokenResponse);
	}

}
