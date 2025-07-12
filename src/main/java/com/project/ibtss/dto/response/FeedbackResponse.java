package com.project.ibtss.dto.response;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FeedbackResponse {
    private Integer feedbackId;
    private String customerName;
    private String driverName;
    private String driverPhone;
    private String content;
    private String status;
    private Integer rating;
    private LocalDateTime createdDate;
}
