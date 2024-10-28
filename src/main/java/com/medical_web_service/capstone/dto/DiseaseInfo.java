package com.medical_web_service.capstone.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiseaseInfo {

    private String name;
    private List<String> details;
    private Map<String, List<String>> symptomsByCategory = new HashMap<>(); // 빈 HashMap으로 초기화

    // getter 및 setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public Map<String, List<String>> getSymptomsByCategory() {
        return symptomsByCategory;
    }

    public void setSymptomsByCategory(Map<String, List<String>> symptomsByCategory) {
        this.symptomsByCategory = symptomsByCategory;
    }
}
