package com.bkopec.confintimes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long id;
    private Long topicId;
    private String content;
    private String authorName;
    private LocalDateTime createdAt;
}