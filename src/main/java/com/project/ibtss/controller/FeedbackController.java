package com.project.ibtss.controller;

import com.project.ibtss.dto.request.FeedbackRequest;
import com.project.ibtss.dto.response.*;
import com.project.ibtss.enums.Position;
import com.project.ibtss.enums.Role;
import com.project.ibtss.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;
//    private FeedbackResponse getFeedbackResponse() {
//        return new FeedbackResponse(1, "Kien Ho", "Khanh Ho", "", 5, LocalDate.now());
//    }
    @PostMapping
    public ApiResponse<FeedbackResponse> postFeedback(@Valid @RequestBody FeedbackRequest feedbackRequest) {
        return ApiResponse.<FeedbackResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(feedbackService.createFeedback(feedbackRequest))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<FeedbackResponse> getFeedback(@Valid @PathVariable Integer id) {
        return ApiResponse.<FeedbackResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(feedbackService.getFeedback(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<FeedbackResponse>> getFeedback() {
        return ApiResponse.<List<FeedbackResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(feedbackService.getAllFeedback())
                .build();
    }
}
