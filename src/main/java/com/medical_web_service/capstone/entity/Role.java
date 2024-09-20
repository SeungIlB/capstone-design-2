package com.medical_web_service.capstone.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum Role {

    DOCTOR("ROLE_DOCTOR", "의사"),
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "관리자");




    private final String key;
    private final String value;

    Role(String key, String value) {
        this.key = key;
        this.value = value;
    }


}
