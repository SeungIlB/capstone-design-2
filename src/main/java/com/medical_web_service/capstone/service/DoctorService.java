package com.medical_web_service.capstone.service;

import com.medical_web_service.capstone.dto.DiseaseHistoryDto;
import com.medical_web_service.capstone.entity.DiseaseHistory;
import com.medical_web_service.capstone.entity.User;
import com.medical_web_service.capstone.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final UserRepository userRepository;
    private final UserService userService;
    public Map<String, Object> getUserDiseaseHistoryWithInfo(Long userId) {
        // 유저 정보 가져오기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));        // 반환할 데이터 생성
        Map<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("username", user.getName()); // 유저 이름 또는 닉네임 추가

        if (user.getDiseaseHistory() != null && !user.getDiseaseHistory().isEmpty()) {
            List<String> diseaseHistoryList = user.getDiseaseHistory().stream()
                    .map(diseaseHistory -> diseaseHistory.getDiseaseName()) // 질병 이름만 가져오기
                    .collect(Collectors.toList());
            result.put("diseaseHistory", diseaseHistoryList);
        } else {
            result.put("diseaseHistory", Collections.emptyList()); // 질병 이력이 없는 경우 빈 리스트
        }

        return result;
    }

}
