package com.medical_web_service.capstone.controller;

import com.medical_web_service.capstone.dto.DiseaseInfo;
import com.medical_web_service.capstone.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiseaseController {

    @Autowired
    private DiseaseService diseaseService;

    // 질병명으로 검색하는 API
    @GetMapping("/disease/search")
    public DiseaseInfo searchDisease(@RequestParam String name) {
        DiseaseInfo diseaseInfo = diseaseService.getDiseaseInfo(name);
        if (diseaseInfo != null) {
            return diseaseInfo;
        } else {
            DiseaseInfo notFoundInfo = new DiseaseInfo();
            notFoundInfo.setName(name);
            notFoundInfo.setDetails(List.of("No details found for the disease: " + name));
            return notFoundInfo;
        }
    }

    @GetMapping("/getSortedDiseases")
    public List<String> getSortedDiseases() {
        return diseaseService.getDiseaseListSorted();
    }
}
