package com.yh.model.dao;

import com.yh.model.dto.Auth;

public interface AuthDao {
    void insertUser(Auth auth);

    Auth findUserByUsername(String username);

    void deleteUser(Long userId);
}
