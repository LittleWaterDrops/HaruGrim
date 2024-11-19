package com.yh.model.service;

import com.yh.model.dto.Users;

public interface UsersService {

	Users getUserById(long id);

	Users getUserByUsername(String username);

	Users getUserByEmail(String email);

	Users createUser(Users user);

	Users updateUser(Users user);

	void deleteUser(long id);

}
