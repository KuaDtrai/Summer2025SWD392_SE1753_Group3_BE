package com.project.ibtss.service;

import com.project.ibtss.dto.request.AdjusmentPaymentRequest;
import com.project.ibtss.dto.request.ChangeTicketRequest;
import com.project.ibtss.dto.request.PaymentConfirmRequest;
import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.dto.response.PaymentProcessResponse;

public interface PaymentTicketService {
    PaymentProcessResponse createPaymentTicket(PaymentSeatRequest request) throws Exception;
    String confirmPaymentTicket(PaymentConfirmRequest request);
    PaymentProcessResponse createAdjusmentPaymentTicket(AdjusmentPaymentRequest request) throws Exception;
    PaymentProcessResponse changeTicket(ChangeTicketRequest request) throws Exception;
}
