package com.project.ibtss.service;

import com.project.ibtss.dto.request.PaymentConfirmRequest;
import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.dto.response.CreatePaymentResponse;

public interface PaymentTicketService {
    CreatePaymentResponse createPaymentTicket(PaymentSeatRequest request) throws Exception;
    String confirmPaymentTicket(PaymentConfirmRequest request);
}
