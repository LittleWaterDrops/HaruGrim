package com.yh.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yh.model.dto.Auth;
import com.yh.model.service.AuthService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Auth auth) {
        authService.signup(auth);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<Auth> login(@RequestBody Auth auth, HttpSession session) {
        Auth loggedInAuth = authService.login(auth, session);
        return ResponseEntity.ok(loggedInAuth);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        authService.logout(session);
        return ResponseEntity.ok("로그아웃 성공");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(HttpSession session) {
        authService.deleteAccount(session);
        return ResponseEntity.ok("회원 탈퇴 성공");
    }
}
