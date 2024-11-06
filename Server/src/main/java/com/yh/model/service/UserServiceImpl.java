package com.yh.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yh.model.dao.UserDao;
import com.yh.model.dto.User;

/**
 * 빈으로 등록될 수 있도록 @Service를 선언한다.
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao dao;

	public UserServiceImpl(UserDao dao) {
		this.dao = dao;
	}

	@Override
	public int insertUser(User user) {
		return dao.insertUser(user);
	}

	@Override
	public User getUserById(String id) {
		return dao.selectById(id);
	}
}
