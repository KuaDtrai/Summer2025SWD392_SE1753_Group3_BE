package com.project.ibtss.service;

import com.project.ibtss.dto.request.FeedbackRequest;
import com.project.ibtss.dto.response.FeedbackResponse;

import java.util.List;

public interface FeedbackService {
    FeedbackResponse getFeedback(Integer id);
    List<FeedbackResponse> getAllFeedback();
    FeedbackResponse createFeedback(FeedbackRequest feedbackRequest);
}
