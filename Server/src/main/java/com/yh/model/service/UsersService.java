package com.yh.model.service;

import com.yh.model.dto.Users;

public interface UsersService {

    Users getUserById(long id);

    Users getUserByUsernameAndPassword(String username, String passwordHash);

    Users getUserByEmail(String email);

    Users createUser(Users user);

    Users updateUser(Users user);

    void deleteUser(long id);

    // 비밀번호 검증 메소드
    boolean validatePassword(String inputPassword, String storedPasswordHash);

    // 비밀번호 해시화 메소드 추가
    String hashPassword(String password);  // 비밀번호 해시화 메소드
}
