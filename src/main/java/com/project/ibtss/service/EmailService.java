package com.project.ibtss.service;

import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.model.Tickets;
import jakarta.mail.MessagingException;

import java.util.List;

public interface EmailService {
    ApiResponse<String> sendEmail(List<Tickets> tickets, String subject, String body) throws MessagingException;
}
