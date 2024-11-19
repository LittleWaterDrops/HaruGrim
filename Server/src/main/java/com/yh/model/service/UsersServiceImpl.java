package com.yh.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yh.model.dao.UsersDao;
import com.yh.model.dto.Users;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersDao usersdao; 

    @Override
    public Users getUserById(long id) {
        return usersdao.findById(id);
    }

    @Override
    public Users getUserByUsername(String username) {
        return usersdao.findByUsername(username);
    }

    @Override
    public Users getUserByEmail(String email) {
        return usersdao.findByEmail(email);
    }

    @Override
    public Users createUser(Users user) {
        usersdao.saveUser(user); // 사용자 저장
        return user;
    }

    @Override
    public Users updateUser(Users user) {
        usersdao.updateUser(user); // 사용자 업데이트
        return user;
    }

    @Override
    public void deleteUser(long id) {
        usersdao.deleteUser(id); // 사용자 삭제
    }
}
