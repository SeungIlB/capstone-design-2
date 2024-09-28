package com.medical_web_service.capstone.dto;


import com.medical_web_service.capstone.entity.Board;
import com.medical_web_service.capstone.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CommentDto {
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class CreateCommentDto{
        private String comment;
        private final String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        private final String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        private User user;
        private Board board;
        private String writer;
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class UpdateCommentDto{
        private String comment;
        private final String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        private User user;
        private Board board;
        private String writer;
    }

}
