package com.medical_web_service.capstone.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(name = "profile_picture")
    private String picture;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String nickname, String email, String picture, String gender, String age, Role role) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.picture = picture;
        this.gender = gender;
        this.age = age;
        this.role = role;
    }

    public void update(String nickname, String picture) {
        this.nickname = nickname;
        this.picture = picture;
    }
}