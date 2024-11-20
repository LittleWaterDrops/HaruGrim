//package com.yh.model.service;
//
//import org.springframework.stereotype.Service;
//
//import com.yh.model.dto.Users;
//
//import jakarta.servlet.http.HttpSession;
//
//@Service
//public class AuthService {
//
//    private final UsersService usersService;
//
//    public AuthService(UsersService usersService) {
//        this.usersService = usersService;
//    }
//
//    // 로그인 처리 (세션에 사용자 정보 저장)
//    public boolean login(HttpSession session, String username, String password) {
//        Users user = usersService.getUserByUsername(username);
//        if (user != null && usersService.validatePassword(password, user.getPasswordHash())) {
//            session.setAttribute("user", user);  // 로그인한 사용자 정보를 세션에 저장
//            return true;
//        }
//        return false;  // 로그인 실패
//    }
//
//    // 로그아웃 처리 (세션 종료)
//    public void logout(HttpSession session) {
//        session.invalidate();  // 세션 무효화
//    }
//}
