package com.yh.model.service;

import com.yh.model.dto.User;

public interface UserService {
    User getProfile(Long userId);

    User updateProfile(User user);
}
