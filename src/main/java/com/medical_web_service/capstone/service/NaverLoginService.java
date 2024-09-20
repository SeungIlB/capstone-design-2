package com.medical_web_service.capstone.service;

import com.medical_web_service.capstone.dto.NaverTokenResponseDto;
import com.medical_web_service.capstone.dto.NaverUserInfoResponseDto;
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
public class NaverLoginService {

    private final WebClient webClient;
    private final UserRepository userRepository;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String redirectUri;

    public String getAccessToken(String code) {
        try {
            NaverTokenResponseDto response = webClient.post()
                    .uri("https://nid.naver.com/oauth2.0/token")
                    .bodyValue("grant_type=authorization_code" +
                            "&client_id=" + clientId +
                            "&client_secret=" + clientSecret +  // client secret 추가
                            "&redirect_uri=" + redirectUri +  // redirect URI 추가
                            "&code=" + code)
                    .retrieve()
                    .bodyToMono(NaverTokenResponseDto.class)
                    .block();

            if (response != null) {
                log.info("Naver Access Token: {}", response.getAccessToken());
                return response.getAccessToken();
            }
        } catch (WebClientResponseException e) {
            log.error("Error fetching access token from Naver", e);
        }
        return null;
    }

    public User getUserInfo(String accessToken) {
        try {
            NaverUserInfoResponseDto userInfo = webClient.get()
                    .uri("https://openapi.naver.com/v1/nid/me")
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(NaverUserInfoResponseDto.class)
                    .block();

            if (userInfo != null) {
                log.info("Naver User Info: {}", userInfo);
                User user = new User();
                user.setName(userInfo.getResponse().getName());
                user.setNickname(userInfo.getResponse().getNickname());
                user.setEmail(userInfo.getResponse().getEmail());
                user.setPicture(userInfo.getResponse().getProfileImage());
                user.setGender(userInfo.getResponse().getGender());
                user.setAge(userInfo.getResponse().getAge());
                user.setRole(Role.USER); // 기본 사용자 역할 설정

                return userRepository.save(user);
            }
        } catch (WebClientResponseException e) {
            log.error("Error fetching user info from Naver", e);
        }
        return null;
    }
}