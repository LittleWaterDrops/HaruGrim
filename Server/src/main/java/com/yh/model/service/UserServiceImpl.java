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
        User user = userDao.findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
        return user;
    }

    @Override
    public User updateProfile(User user) {
        userDao.updateUser(user);
        return getProfile(user.getId());
    }
}
