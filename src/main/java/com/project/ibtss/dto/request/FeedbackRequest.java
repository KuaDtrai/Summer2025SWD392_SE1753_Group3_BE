package com.project.ibtss.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FeedbackRequest {
    private Integer staffId;
    private String content;
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    private Integer rating;
    private LocalDateTime createdDate;
}
