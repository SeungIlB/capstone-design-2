package com.medical_web_service.capstone.dto;

import lombok.*;

/**
 * Please explain the class!!
 *
 * @author : lee
 * @fileName : ChatRequestMsgDto
 * @since : 1/18/24
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ChatRequestMsgDto {

    private String role;

    private String content;

    @Builder
    public ChatRequestMsgDto(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
