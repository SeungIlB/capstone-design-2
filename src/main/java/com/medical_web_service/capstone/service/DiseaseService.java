package com.medical_web_service.capstone.service;

import com.medical_web_service.capstone.dto.DiseaseInfo;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DiseaseService {

    private static final String DISEASE_LIST_PATH = "C:\\Users\\82109\\capstone-design-2\\src\\main\\resources\\static\\csv\\diseases_list.csv";
    private static final String ALL_DISEASE_DETAILS_PATH = "C:\\Users\\82109\\capstone-design-2\\src\\main\\resources\\static\\csv\\all_disease_details.csv";
    private static final String DISEASE_SYMPTOMS_PATH = "C:\\Users\\82109\\capstone-design-2\\src\\main\\resources\\static\\csv\\disease_symptoms.csv";

    private Map<String, DiseaseInfo> diseaseInfoMap;

    public DiseaseService() {
        diseaseInfoMap = new HashMap<>();
        loadDiseaseData();
        loadDiseaseSymptoms();
    }
    // 질병 목록을 가져옴
    public List<String> getDiseaseListSorted() {
        List<String> diseaseNames = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(DISEASE_LIST_PATH))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                diseaseNames.add(nextLine[0]); // CSV의 첫 번째 열에 질병명이 있다고 가정
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        // Comparator를 사용하여 정렬
        Collections.sort(diseaseNames, new DiseaseNameComparator());

        return diseaseNames;
    }




    // 질병 상세 정보를 로드하는 메서드
    private void loadDiseaseData() {
        try (CSVReader reader = new CSVReader(new FileReader(ALL_DISEASE_DETAILS_PATH))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line.length < 2) {
                    continue;  // 배열 길이가 2 미만인 경우 건너뜁니다.
                }
                String diseaseName = line[0];
                String detail = line[1];

                DiseaseInfo diseaseInfo = diseaseInfoMap.getOrDefault(diseaseName, new DiseaseInfo());
                diseaseInfo.setName(diseaseName);

                List<String> details = diseaseInfo.getDetails() == null ? new ArrayList<>() : diseaseInfo.getDetails();
                details.add(detail);
                diseaseInfo.setDetails(details);

                diseaseInfoMap.put(diseaseName, diseaseInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 질병 증상 정보를 로드하는 메서드 (항목과 증상 결합)
    private void loadDiseaseSymptoms() {
        try (CSVReader reader = new CSVReader(new FileReader(DISEASE_SYMPTOMS_PATH))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line.length < 3) {
                    continue;  // 배열 길이가 3 미만인 경우 건너뜁니다.
                }
                String diseaseName = line[0];  // 질병명
                String category = line[1];     // 항목
                String symptom = line[2];      // 증상

                DiseaseInfo diseaseInfo = diseaseInfoMap.getOrDefault(diseaseName, new DiseaseInfo());
                diseaseInfo.setName(diseaseName);

                // 항목과 증상을 결합하여 하나의 문자열로 저장
                List<String> symptoms = diseaseInfo.getSymptoms() == null ? new ArrayList<>() : diseaseInfo.getSymptoms();
                symptoms.add(category + ": " + symptom);
                diseaseInfo.setSymptoms(symptoms);

                diseaseInfoMap.put(diseaseName, diseaseInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 질병명을 입력받아 DiseaseInfo 객체 반환
    public DiseaseInfo getDiseaseInfo(String diseaseName) {
        return diseaseInfoMap.getOrDefault(diseaseName, null);
    }
}
