package com.yh.model.dao;

import com.yh.model.dto.Users;

public interface UsersDao {
	Users findById(long id);

	Users findByUsernameAndPassword(String username, String passwordHash);

	Users findByEmail(String email);

	int createUser(Users user);

	int updateUser(Users user);

	void deleteUser(long id);
}
