package com.medical_web_service.capstone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverUserInfoResponseDto {

    private String resultcode;
    private String message;
    private Response response;

    @Getter
    @Setter
    public static class Response {

        private String id;

        private String nickname;

        private String name;

        private String email;

        private String gender;

        private String age;

        @JsonProperty("profile_image")
        private String profileImage;
    }
}