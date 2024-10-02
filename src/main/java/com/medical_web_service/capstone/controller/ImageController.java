package com.medical_web_service.capstone.controller;

import com.medical_web_service.capstone.entity.Image;
import com.medical_web_service.capstone.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        try {
            Image image = imageService.uploadImage(file, userId);
            return ResponseEntity.ok("이미지 업로드 성공: " + image.getImagename());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("이미지 업로드 실패: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }

    // 이미지 파일을 반환하는 엔드포인트
    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long imageId) {
        byte[] imageBytes = imageService.getImageFile(imageId);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");  // 이미지 타입 설정 (예: JPEG)

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}
