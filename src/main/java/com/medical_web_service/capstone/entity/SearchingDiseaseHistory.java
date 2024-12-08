package com.medical_web_service.capstone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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
