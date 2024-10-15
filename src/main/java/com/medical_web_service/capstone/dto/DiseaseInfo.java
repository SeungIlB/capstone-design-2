package com.medical_web_service.capstone.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DiseaseInfo {
    private String name;
    private List<String> details;
    private List<String> symptoms;
}
