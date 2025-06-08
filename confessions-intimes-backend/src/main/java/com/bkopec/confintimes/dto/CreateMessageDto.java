package com.bkopec.confintimes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateMessageDto {
    @NotBlank(message = "Content cannot be blank")
    private String content;

    @NotBlank(message = "Author name cannot be blank")
    @Size(max = 100, message = "Author name cannot exceed 100 characters")
    private String authorName;
}