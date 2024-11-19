package com.yh.model.dao;

import com.yh.model.dto.Users;

public interface UsersDao {
	Users findById(long id);

	Users findByUsername(String username);

	Users findByEmail(String email);

	int saveUser(Users user);

	int updateUser(Users user);

	void deleteUser(long id);
}
