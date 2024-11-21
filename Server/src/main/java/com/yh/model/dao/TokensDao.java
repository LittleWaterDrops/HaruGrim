package com.yh.model.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yh.model.dto.Tokens;

public interface TokensDao {
	// 로그인시 토큰 생성을 위한 메소드
	void insertToken(Long userId, String tokenType, String token, Date expiresAt);

	void deleteTokensByUserId(Long userId); // 로그아웃시 토큰 삭제

	void deleteTokenByValue(String token); 
	
	Tokens findTokenByValueAndType(@Param("token") String token, @Param("tokenType") String tokenType);

//	List<Tokens> findByUserId(@Param("userId") Long userId);
}
