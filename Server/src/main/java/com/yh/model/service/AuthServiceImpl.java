package com.yh.model.service;

import org.springframework.stereotype.Service;

import com.yh.model.dao.AuthDao;
import com.yh.model.dao.TokensDao;
import com.yh.model.dao.UserDao;
import com.yh.model.dto.Auth;
import com.yh.model.dto.TokenResponse;
import com.yh.model.dto.Tokens;
import com.yh.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {
	private final TokensDao tokensDao;
	private final UserDao userDao;
	private final AuthDao authDao;
	private final JwtUtil jwtUtil;

	public AuthServiceImpl(AuthDao authDao, JwtUtil jwtUtil, UserDao userDao, TokensDao tokensDao) {
		this.userDao = userDao;
		this.authDao = authDao;
		this.jwtUtil = jwtUtil;
		this.tokensDao = tokensDao;
	}

	@Override
	public void signup(Auth auth) {
		authDao.insertUser(auth);
	}

	@Override
	public TokenResponse login(Auth auth) {
	    Auth foundAuth = authDao.findUserByUserEmail(auth.getEmail());

	    // 이메일이 틀린 경우
	    if (foundAuth == null) {
	        throw new IllegalArgumentException("존재하지 않는 회원입니다.");
	    }

	    // 비밀번호가 틀린 경우
	    if (!foundAuth.getPassword().equals(auth.getPassword())) {
	        throw new IllegalArgumentException("비밀번호가 틀립니다.");
	    }

	    String accessToken = jwtUtil.generateAccessToken(foundAuth.getId());
	    String refreshToken = jwtUtil.generateRefreshToken(foundAuth.getId());

	    tokensDao.insertToken(foundAuth.getId(), "REFRESH", refreshToken, jwtUtil.getRefreshTokenExpiryDate());

	    return new TokenResponse(accessToken, refreshToken);
	}


	@Override
	public TokenResponse refreshToken(String refreshToken) {
		// Refresh Token 검증
		Long userId = jwtUtil.validateRefreshToken(refreshToken);

		// 데이터베이스에서 Refresh Token 확인
		Tokens token = tokensDao.findTokenByValueAndType(refreshToken, "REFRESH");
		if (token == null) {
			throw new IllegalArgumentException("Invalid Refresh Token");
		}

		// 새로운 Access Token 생성
		String newAccessToken = jwtUtil.generateAccessToken(userId);

		return new TokenResponse(newAccessToken, refreshToken);
	}

	@Override
	public void logout(String accessToken) {
	    // Access Token 검증
	    Long userId = jwtUtil.validateAccessToken(accessToken);
	    System.out.println(userId);

	    if (userId == null) {
	        throw new IllegalArgumentException("Invalid Access Token");
	    }

	    // Refresh Token 삭제
	    tokensDao.deleteTokenByUserIdAndType(userId, "REFRESH");
	}


	@Override
	public void deleteAccount(String accessToken) {
	    // Access Token 검증
	    Long userId = jwtUtil.validateAccessToken(accessToken);

	    if (userId == null) {
	        throw new IllegalArgumentException("Invalid Access Token");
	    }

	    // 1. Refresh Token 삭제
	    tokensDao.deleteTokenByUserIdAndType(userId, "REFRESH");

	    // 2. 사용자 계정 삭제
	    userDao.deleteUserById(userId);
	}
	
	@Override
    public boolean isEmailTaken(String email) {
        return userDao.findByEmail(email) != null;
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return userDao.findByUsername(username) != null;
    }


}
