package com.project.ibtss.dto.response;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class FeedbackResponse {
    private Integer feedbackId;
    private String customerName;
    private String staffName;
    @NotBlank
    private String content;
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    private Integer rating;
    private LocalDate createdDate;
}
