package com.yh.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yh.model.dto.LoginRequestUsers;
import com.yh.model.dto.Users;
import com.yh.model.service.UsersService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    // 프로필 조회
    @GetMapping("/my")
    public ResponseEntity<Users> getProfile(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Users user = usersService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    // 프로필 수정
    @PatchMapping("/my")
    public ResponseEntity<Users> updateProfile(@RequestBody Users updatedUser, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        updatedUser.setId(userId); // 세션에 저장된 사용자 ID로 수정
        Users user = usersService.updateUser(updatedUser);
        return ResponseEntity.ok(user);
    }

    // 회원 가입
    @PostMapping("/auth/signup")
    public ResponseEntity<String> signup(@RequestBody Users newUser) {
        // 비밀번호 해시화 처리
        newUser.setPasswordHash(usersService.hashPassword(newUser.getPasswordHash()));
        usersService.createUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입 성공");
    }

    // 회원 탈퇴
    @DeleteMapping("/auth/delete")
    public ResponseEntity<String> deleteAccount(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        usersService.deleteUser(userId);
        session.invalidate(); // 세션 무효화
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("회원 탈퇴 완료");
    }

    // 자체 로그인
    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestUsers userCredentials, HttpSession session) {
        Users user = usersService.getUserByUsernameAndPassword(userCredentials.getUsername(), userCredentials.getPasswordHash());
        if (user == null) {
            System.out.println("로그인 실패");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 실패");
        }
        session.setAttribute("userId", user.getId()); // 세션에 사용자 ID 저장
        System.out.println("로그인 성공");
        return ResponseEntity.ok("로그인 성공");
    }


    // 로그아웃
    @GetMapping("/auth/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return ResponseEntity.ok("로그아웃 성공");
    }
}
