package com.medical_web_service.capstone.controller;

import com.medical_web_service.capstone.entity.User;
import com.medical_web_service.capstone.repository.UserRepository;
import com.medical_web_service.capstone.service.DoctorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/get/{userId}/disease-history")
    public ResponseEntity<Map<String, Object>> getUserDiseaseHistory(@PathVariable Long userId) {
        // Service 로직 대신 Repository를 사용하여 데이터를 직접 가져옴

        Map<String, Object> userDisease = doctorService.getUserDiseaseHistoryWithInfo(userId);
        // HTTP 응답 반환
        return ResponseEntity.ok(userDisease);
    }
}
