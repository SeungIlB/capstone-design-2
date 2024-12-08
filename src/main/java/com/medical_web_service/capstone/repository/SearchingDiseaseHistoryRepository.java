package com.medical_web_service.capstone.repository;

import com.medical_web_service.capstone.entity.SearchingDiseaseHistory;
import com.medical_web_service.capstone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchingDiseaseHistoryRepository extends JpaRepository<SearchingDiseaseHistory, Long> {
    List<SearchingDiseaseHistory> findByUser(User user);

}
