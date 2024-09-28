package com.medical_web_service.capstone.dto;

import com.medical_web_service.capstone.entity.Board;

public class BoardMapper {
    public static BoardDto.PostDetailsDTO toDto(Board board) {
        return new BoardDto.PostDetailsDTO(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getWriter(),
                board.getUser().getId(), // userId 추가
                board.getPostedTime(),
                board.getUpdatedTime()
        );
    }
}
