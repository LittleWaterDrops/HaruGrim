package com.yh.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	// 하드코딩된 Secret Key (64바이트 이상)
	private final Key key = Keys.hmacShaKeyFor(
			"your-512-bit-secret-your-512-bit-secret-your-512-bit-secret-your-512-bit-secret".getBytes());

	private final long accessTokenValidity = 1000 * 60 * 30; // 30 minutes
	private final long refreshTokenValidity = 1000 * 60 * 60 * 24 * 7; // 7 days

	// Access Token 생성
	public String generateAccessToken(Long userId) {
		return Jwts.builder().setSubject(String.valueOf(userId)) // 사용자 ID를 Subject로 설정
				.setIssuedAt(new Date()) // 발급 시각
				.setExpiration(getAccessTokenExpiryDate()) // 만료 시각 설정
				.signWith(key, SignatureAlgorithm.HS512) // 서명 알고리즘
				.compact(); // JWT 생성
	}

	// Refresh Token 생성
	public String generateRefreshToken(Long userId) {
		return Jwts.builder().setSubject(String.valueOf(userId)).setIssuedAt(new Date())
				.setExpiration(getRefreshTokenExpiryDate()) // 만료 시간 설정
				.signWith(key, SignatureAlgorithm.HS512) // 서명 알고리즘
				.compact();
	}

	// Access Token 만료 시간 계산
	public Date getAccessTokenExpiryDate() {
		return new Date(System.currentTimeMillis() + accessTokenValidity);
	}

	// Refresh Token 만료 시간 계산
	public Date getRefreshTokenExpiryDate() {
		return new Date(System.currentTimeMillis() + refreshTokenValidity);
	}

	// Access Token 검증
	public Long validateAccessToken(String token) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(key) // Secret Key 검증
					.build().parseClaimsJws(token) // JWT 파싱
					.getBody();

			System.out.println("Token Claims: " + claims); // 디버깅 로그
			return Long.parseLong(claims.getSubject());
		} catch (ExpiredJwtException e) {
			System.out.println("Expired Token: " + e.getMessage());
			throw new IllegalStateException("Access Token has expired");
		} catch (JwtException e) {
			System.out.println("Invalid Token: " + e.getMessage());
			throw new IllegalStateException("Invalid Access Token");
		}
	}

	// Refresh Token 검증
	public Long validateRefreshToken(String token) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
			return Long.parseLong(claims.getSubject());
		} catch (JwtException e) {
			throw new IllegalStateException("Invalid Refresh Token");
		}
	}
}
