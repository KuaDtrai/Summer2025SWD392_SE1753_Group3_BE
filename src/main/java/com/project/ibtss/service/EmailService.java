package com.project.ibtss.service;

import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.model.Tickets;
import jakarta.mail.MessagingException;

import java.util.List;

public interface EmailService {
    ApiResponse<String> sendEmail(List<Tickets> tickets, String subject, String body) throws MessagingException;
    ApiResponse<String> sendVerificationEmail(String email, String verificationCode) throws MessagingException;
    ApiResponse<String> sendRefundNotificationEmail(String email, float refundAmount) throws MessagingException;
    ApiResponse<String> sendTicketChangeSuccessEmail(String email, List<Tickets> newTickets) throws MessagingException;
    void sendFeedbackReplyEmail(String email, String content) throws MessagingException;
}
