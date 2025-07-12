package com.project.ibtss.service;

import com.project.ibtss.dto.request.FeedbackRequest;
import com.project.ibtss.dto.request.ReplyFeedbackRequest;
import com.project.ibtss.dto.response.FeedbackResponse;
import jakarta.mail.MessagingException;

import java.util.List;

public interface FeedbackService {
    FeedbackResponse getFeedback(Integer id);
    List<FeedbackResponse> getAllFeedback();
    FeedbackResponse createFeedback(FeedbackRequest feedbackRequest);
    void replyFeedback(ReplyFeedbackRequest request) throws MessagingException;
}
