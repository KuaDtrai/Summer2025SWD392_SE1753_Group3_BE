package com.project.ibtss.service;

import com.project.ibtss.dto.request.PaymentConfirmRequest;
import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.dto.response.CreatePaymentResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface PaymentTicketService {
    CreatePaymentResponse createPaymentTicket(PaymentSeatRequest request);
    CreatePaymentResponse confirmPaymentTicket(PaymentConfirmRequest request);
}
