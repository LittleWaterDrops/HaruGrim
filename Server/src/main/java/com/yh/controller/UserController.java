package com.yh.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.yh.model.dto.User;
import com.yh.model.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/my")
    public ResponseEntity<User> getProfile(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        User user = userService.getProfile(userId);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/my")
    public ResponseEntity<User> updateProfile(@RequestBody User user, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        user.setId(userId);
        User updatedUser = userService.updateProfile(user);
        return ResponseEntity.ok(updatedUser);
    }
}
