package com.medical_web_service.capstone.service;

import com.medical_web_service.capstone.entity.Image;
import com.medical_web_service.capstone.entity.User;
import com.medical_web_service.capstone.repository.ImageRepository;
import com.medical_web_service.capstone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    @Value("${file.upload-dir}") // application.yml 파일에서 경로를 가져옴
    private String uploadDir;

    public Image uploadImage(MultipartFile file, Long userId) throws IOException {
        // 파일명을 유니크하게 설정하기 위해 UUID 사용
        String originalFilename = file.getOriginalFilename();
        String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

        // 디렉토리가 존재하지 않으면 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();  // 디렉토리가 존재하지 않으면 생성
        }

        // 파일을 지정된 디렉토리에 저장
        File destinationFile = new File(directory, uniqueFilename);
        file.transferTo(destinationFile);

        // 사용자 정보 조회
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
        User user = userOptional.get();

        // 이미지 엔티티 생성 및 저장
        Image image = new Image();
        image.setImagePath(destinationFile.getAbsolutePath()); // 저장된 파일 경로
        image.setImagename(uniqueFilename); // 저장된 파일명
        image.setUser(user); // 사용자 정보 설정

        return imageRepository.save(image); // DB에 이미지 정보 저장
    }

    // 이미지 파일 경로로부터 파일 바이트 배열을 반환하는 메서드
    public byte[] getImageFile(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found"));

        try {
            Path imagePath = Paths.get(image.getImagePath());  // 저장된 이미지 경로 확인
            return Files.readAllBytes(imagePath);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not read image file");
        }
    }
}