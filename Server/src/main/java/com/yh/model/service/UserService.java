package com.yh.model.service;

import com.yh.model.dto.User;

public interface UserService {
	
	// 추가
	int insertUser(User user);
	
	// 사용자 조회(id)
	User getUserById(String id);
	
}
