package com.yh.model.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.yh.model.dao.UsersDao;
import com.yh.model.dto.Users;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersDao usersDao;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsersServiceImpl(UsersDao usersDao) {
        this.usersDao = usersDao;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Users getUserById(long id) {
        return usersDao.findById(id);
    }

    @Override
    public Users getUserByUsernameAndPassword(String username, String passwordHash) {
        return usersDao.findByUsernameAndPassword(username, passwordHash);
    }


    @Override
    public Users getUserByEmail(String email) {
        return usersDao.findByEmail(email);
    }

    @Override
    public Users createUser(Users user) {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));  // 비밀번호 해시화
        usersDao.createUser(user);  // 사용자 저장
        return user;
    }

    @Override
    public Users updateUser(Users user) {
        usersDao.updateUser(user);  // 사용자 업데이트
        return user;
    }

    @Override
    public void deleteUser(long id) {
        usersDao.deleteUser(id);  // 사용자 삭제
    }

    @Override
    public boolean validatePassword(String inputPassword, String storedPasswordHash) {
        return passwordEncoder.matches(inputPassword, storedPasswordHash);  // 비밀번호 비교
    }

    // 비밀번호 해시화 메소드
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);  // 비밀번호 해시화
    }
}
