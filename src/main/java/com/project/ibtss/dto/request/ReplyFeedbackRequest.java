package com.project.ibtss.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReplyFeedbackRequest {
    int feedbackId;
    String content;
}
