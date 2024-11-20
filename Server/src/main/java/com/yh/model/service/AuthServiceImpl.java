package com.yh.model.service;

import org.springframework.stereotype.Service;
import com.yh.model.dao.AuthDao;
import com.yh.model.dto.Auth;

import jakarta.servlet.http.HttpSession;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthDao authDao;

    public AuthServiceImpl(AuthDao authDao) {
        this.authDao = authDao;
    }

    @Override
    public void signup(Auth auth) {
        authDao.insertUser(auth);
    }

    @Override
    public Auth login(Auth auth, HttpSession session) {
        Auth foundAuth = authDao.findUserByUsername(auth.getUsername());
        if (foundAuth != null && foundAuth.getPassword().equals(auth.getPassword())) {
            session.setAttribute("userId", foundAuth.getUserId());
            return foundAuth;
        }
        throw new IllegalArgumentException("로그인 실패");
    }

    @Override
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @Override
    public void deleteAccount(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            authDao.deleteUser(userId);
            session.invalidate();
        }
    }
}
