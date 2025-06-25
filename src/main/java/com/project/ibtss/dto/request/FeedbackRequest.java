package com.project.ibtss.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class FeedbackRequest {

    @NotNull(message = "Staff ID is required")
    private Integer staffId;

    @NotBlank(message = "Content is required")
    @Size(max = 500, message = "Content cannot exceed 500 characters")
    private String content;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    private Integer rating;
}