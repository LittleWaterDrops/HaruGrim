package com.yh.model.dao;

import com.yh.model.dto.User;

public interface UserDao {
	User findUserById(Long userId); // 회원 정보 조회(로그인 사용자)

	void updateUser(User user); // 회원 정보 업데이트(로그인 사용자)

	void deleteUserById(Long userId); // 회원탈퇴시 필요(AuthServiceImpl에서 호출)

	User findByEmail(String email);

	User findByUsername(String username);
}
