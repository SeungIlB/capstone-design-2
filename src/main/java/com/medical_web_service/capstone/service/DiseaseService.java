package com.medical_web_service.capstone.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.medical_web_service.capstone.dto.DiseaseInfo;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.text.Collator;
import java.util.*;

@Service
public class DiseaseService {

    private static final String ALL_DISEASE_DETAILS_PATH = "C:\\Users\\82109\\capstone-design-2\\src\\main\\resources\\static\\csv\\all_disease_details.csv";
    private static final String DISEASE_SYMPTOMS_PATH = "C:\\Users\\82109\\capstone-design-2\\src\\main\\resources\\static\\csv\\disease_symptoms.csv";
    private Map<String, DiseaseInfo> diseaseInfoMap;

    public DiseaseService() {
        diseaseInfoMap = new HashMap<>();
        loadDiseaseData();
        loadDiseaseSymptoms();  // 새 메서드 추가
    }

    // 질병 상세 정보를 로드하는 메서드
    private void loadDiseaseData() {
        try (CSVReader reader = new CSVReader(new FileReader(ALL_DISEASE_DETAILS_PATH))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line.length < 2) {
                    continue;
                }
                String diseaseName = line[0];
                String detail = line[1];

                // DiseaseInfo 객체 생성 또는 기존 객체 가져오기
                DiseaseInfo diseaseInfo = diseaseInfoMap.getOrDefault(diseaseName, new DiseaseInfo());
                diseaseInfo.setName(diseaseName);

                // details 필드에 CSV의 상세 정보 항목을 리스트로 추가
                List<String> details = diseaseInfo.getDetails() == null ? new ArrayList<>() : diseaseInfo.getDetails();
                details.add(detail);
                diseaseInfo.setDetails(details);

                diseaseInfoMap.put(diseaseName, diseaseInfo);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    // 질병 증상 데이터를 로드하는 메서드
    public void loadDiseaseSymptoms() {
        try (CSVReader reader = new CSVReader(new FileReader(DISEASE_SYMPTOMS_PATH))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line.length < 3) {
                    continue; // Ensure the line has at least 3 columns
                }

                String diseaseName = line[0]; // 질병명
                String category = line[1];    // 증상 유형 (예: 증상, 병원 방문 필요 증상 등)
                String content = line[2];     // 증상 또는 관리 방법의 내용

                // Retrieve or create DiseaseInfo for the specified disease
                DiseaseInfo diseaseInfo = diseaseInfoMap.getOrDefault(diseaseName, new DiseaseInfo());
                diseaseInfo.setName(diseaseName);

                // Retrieve or initialize list of contents for the specified category
                Map<String, List<String>> symptomsByCategory = diseaseInfo.getSymptomsByCategory();
                List<String> categoryContents = symptomsByCategory.getOrDefault(category, new ArrayList<>());
                categoryContents.add(content);

                // Update the category in the map and set it in DiseaseInfo
                symptomsByCategory.put(category, categoryContents);
                diseaseInfo.setSymptomsByCategory(symptomsByCategory);

                // Update the main disease info map
                diseaseInfoMap.put(diseaseName, diseaseInfo);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
    // 질병명을 입력받아 DiseaseInfo 객체 반환
    public DiseaseInfo getDiseaseInfo(String diseaseName) {
        return diseaseInfoMap.getOrDefault(diseaseName, null);
    }

    // DiseaseInfo 객체를 JSON 형식의 문자열로 반환 (이스케이프 문자 제거)
    public String getDiseaseInfoAsFormattedJson(String diseaseName) {
        DiseaseInfo diseaseInfo = getDiseaseInfo(diseaseName);

        if (diseaseInfo == null) {
            return "{}";
        }

        // Jackson ObjectMapper를 사용해 JSON 문자열을 포맷
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);  // JSON을 포맷팅된 출력으로 설정

        try {
            String jsonString = mapper.writeValueAsString(diseaseInfo);
            return StringEscapeUtils.unescapeJson(jsonString);  // 이스케이프 문자 제거
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    // DiseaseService에 전체 질병 리스트 반환 메서드 추가
    public List<DiseaseInfo> getAllDiseases() {
        return new ArrayList<>(diseaseInfoMap.values());
    }
}
