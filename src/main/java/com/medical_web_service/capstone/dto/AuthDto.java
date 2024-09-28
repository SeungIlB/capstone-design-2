package com.medical_web_service.capstone.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

public class AuthDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginDto {
        private String username;
        private String password;

        @Builder
        public LoginDto(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignupDto {
        private String username;
        private String password;
        private String name;
        private String nickname;
        private String phone;

        @Builder
        public SignupDto(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public static SignupDto encodePassword(SignupDto signupDto, String encodedPassword) {
            SignupDto newSignupDto = new SignupDto();
            newSignupDto.username = signupDto.getUsername();
            newSignupDto.password = encodedPassword;
            newSignupDto.name = signupDto.getName();
            newSignupDto.nickname = signupDto.getNickname();
            newSignupDto.phone = signupDto.getPhone();
            return newSignupDto;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TokenDto {
        private String accessToken;
        private String refreshToken;

        public TokenDto(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateDto{
        private String username;
        private String password;
        private String name;
        private String nickname;
        private String newPassword; // 새로운 비밀번호

        private String phone;
        private String label;

        @Builder
        public UpdateDto(String username, String password,String newpassword, String name, String nickname, String phone) {
            this.username = username;
            this.password = password;
            this.newPassword = newpassword; // 새로운 비밀번호
            this.name = name;
            this.nickname = nickname;
            this.phone = phone;}
    }
}