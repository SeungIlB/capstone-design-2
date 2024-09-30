package com.medical_web_service.capstone.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Entity
@Getter
@Setter
public class Image {
    @Id
    @NotNull
    @jakarta.persistence.Id
    private Long id;

    @Column
    private String imagename;


    @Column
    private String path;
}