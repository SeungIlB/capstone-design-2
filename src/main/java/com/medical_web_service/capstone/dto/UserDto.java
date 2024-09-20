package com.medical_web_service.capstone.dto;

import com.medical_web_service.capstone.entity.Role;
import com.medical_web_service.capstone.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDto {

    private Long id;

    private String name;

    private String nickname;

    private String email;

    private String picture;

    private String gender;

    private String age; // 연령대

    private Role role;

    // 생성자

    public UserDto(Long id, String name, String nickname, String email, String picture, String gender, String age, Role role) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.picture = picture;
        this.gender = gender;
        this.age = age;
        this.role = role;
    }
}
