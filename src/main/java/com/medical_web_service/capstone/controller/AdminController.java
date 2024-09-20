package com.medical_web_service.capstone.controller;

import com.medical_web_service.capstone.entity.Role;
import com.medical_web_service.capstone.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/users/{userId}/role")
    public ResponseEntity<Void> changeUserRole(@PathVariable Long userId, @RequestParam Role role) {
        userService.updateUserRole(userId, role);
        return ResponseEntity.ok().build();
    }
}
