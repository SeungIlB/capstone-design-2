package com.medical_web_service.capstone.controller;

import com.medical_web_service.capstone.service.NaverLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class NaverLoginController {

    private final NaverLoginService naverLoginService;

    @GetMapping("/oauth2/code/naver")
    public ResponseEntity<?> naverCallback(@RequestParam("code") String code) {
        String accessToken = naverLoginService.getAccessToken(code);
        if (accessToken != null) {
            naverLoginService.getUserInfo(accessToken);
            return ResponseEntity.ok("Naver login successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Naver login failed");
    }
}