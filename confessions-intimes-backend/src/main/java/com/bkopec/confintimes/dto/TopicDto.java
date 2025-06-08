package com.bkopec.confintimes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {
    private Long id;
    private String subject;
    private String posterName;
    private LocalDateTime createdAt;
    private LocalDateTime lastPostAt;
    private Integer repliesCount;
}