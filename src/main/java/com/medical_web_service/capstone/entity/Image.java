package com.medical_web_service.capstone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    private Long id;

    @Column(name = "image_path", nullable = false)
    private String imagePath; // 사진 경로

    @Column(name = "image_name", nullable = false)
    private String imagename;

    @ManyToOne
    @JoinColumn(name = "user_Id", nullable = false)
    private User user; // FK
}