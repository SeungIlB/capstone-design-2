package com.medical_web_service.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseHistoryDto {

    private String diseaseName;
    private String completeCureOrNot;
    private String dateOnOnset;
    private Long userId; // 사용자 ID 정보 포함
}
