package com.yh.model.service;

import org.springframework.stereotype.Service;
import com.yh.model.dao.UserDao;
import com.yh.model.dto.User;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getProfile(Long userId) {
        return userDao.findUserById(userId);
    }

    @Override
    public User updateProfile(User user) {
        userDao.updateUser(user);
        return getProfile(user.getId());
    }
}
