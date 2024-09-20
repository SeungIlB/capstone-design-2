package com.medical_web_service.capstone.service;

import com.medical_web_service.capstone.dto.KakaoTokenResponseDto;
import com.medical_web_service.capstone.dto.KakaoUserInfoResponseDto;
import com.medical_web_service.capstone.entity.Role;
import com.medical_web_service.capstone.entity.User;
import com.medical_web_service.capstone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoLoginService {

    private final WebClient webClient;
    private final UserRepository userRepository;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    public String getAccessToken(String code) {
        try {
            KakaoTokenResponseDto response = webClient.post()
                    .uri("https://kauth.kakao.com/oauth/token")
                    .bodyValue("grant_type=authorization_code" +
                            "&client_id=" + clientId +
                            "&client_secret=" + clientSecret +  // client secret 추가
                            "&redirect_uri=" + redirectUri +  // redirect URI 추가
                            "&code=" + code)
                    .retrieve()
                    .bodyToMono(KakaoTokenResponseDto.class)
                    .block();

            if (response != null) {
                log.info("Kakao Access Token: {}", response.getAccessToken());
                return response.getAccessToken();
            }
        } catch (WebClientResponseException e) {
            log.error("Error fetching access token from Kakao", e);
        }
        return null;
    }

    public User getUserInfo(String accessToken) {
        try {
            KakaoUserInfoResponseDto userInfo = webClient.get()
                    .uri("https://kapi.kakao.com/v2/user/me")
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(KakaoUserInfoResponseDto.class)
                    .block();

            if (userInfo != null) {
                log.info("Kakao User Info: {}", userInfo);
                User user = new User();
                user.setName(userInfo.getKakaoAccount().getProfile().getNickName());
                user.setNickname(userInfo.getKakaoAccount().getProfile().getNickName());
                user.setEmail(userInfo.getKakaoAccount().getEmail());
                user.setPicture(userInfo.getKakaoAccount().getProfile().getProfileImageUrl());
                user.setGender(userInfo.getKakaoAccount().getGender());
                user.setAge(userInfo.getKakaoAccount().getAgeRange());
                user.setRole(Role.USER); // 기본 사용자 역할 설정

                return userRepository.save(user);
            }
        } catch (WebClientResponseException e) {
            log.error("Error fetching user info from Kakao", e);
        }
        return null;
    }
}