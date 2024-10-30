package com.medical_web_service.capstone.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class SearchingDiseaseHistory {
    @Id
    private Long id;

    @Column(nullable = false)
    private String diseaseName;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
