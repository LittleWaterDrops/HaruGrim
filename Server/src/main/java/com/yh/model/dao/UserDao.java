package com.yh.model.dao;

import com.yh.model.dto.User;

public interface UserDao {
	
	// 추가
	int insertUser(User user);
	
	// 사용자 조회(id)
	User selectById(String id);
}
