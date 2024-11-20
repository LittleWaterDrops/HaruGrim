package com.yh.model.dao;

import com.yh.model.dto.User;

public interface UserDao {
    User findUserById(Long userId);

    void updateUser(User user);
}
