package com.medical_web_service.capstone.repository;

import com.medical_web_service.capstone.entity.Image;
import com.medical_web_service.capstone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long>{

}
