package com.bkopec.confintimes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateTopicDto {
    @NotBlank(message = "Subject cannot be blank")
    @Size(max = 255, message = "Subject cannot exceed 255 characters")
    private String subject;

    @NotBlank(message = "Poster name cannot be blank")
    @Size(max = 100, message = "Poster name cannot exceed 100 characters")
    private String posterName;

    @NotBlank(message = "Original message content cannot be blank")
    private String content;
}