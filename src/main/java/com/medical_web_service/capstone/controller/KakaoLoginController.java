package com.medical_web_service.capstone.controller;

import com.medical_web_service.capstone.service.KakaoLoginService;
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
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/oauth2/code/kakao")
    public ResponseEntity<?> kakaoCallback(@RequestParam("code") String code) {
        String accessToken = kakaoLoginService.getAccessToken(code);
        if (accessToken != null) {
            kakaoLoginService.getUserInfo(accessToken);
            return ResponseEntity.ok("Kakao login successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Kakao login failed");
    }
}