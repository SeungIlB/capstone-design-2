package com.medical_web_service.capstone.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiseaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String diseaseName;

    @Column(nullable = false)
    private String completeCureOrNot;

    @Column(nullable = false)
    private String dateOnOnset;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 필드를 초기화하는 생성자 추가
    public DiseaseHistory(String diseaseName, String completeCureOrNot, String dateOnOnset, User user) {
        this.diseaseName = diseaseName;
        this.completeCureOrNot = completeCureOrNot;
        this.dateOnOnset = dateOnOnset;
        this.user = user;
    }
    // 업데이트 메서드 추가
    public void update(String diseaseName, String completeCureOrNot, String dateOnOnset) {
        this.diseaseName = diseaseName;
        this.completeCureOrNot = completeCureOrNot;
        this.dateOnOnset = dateOnOnset;
    }
}
