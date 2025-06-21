package com.project.ibtss.service;

import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.model.Payment;

public interface PaymentService {
    Payment createPayment(PaymentSeatRequest request);
}
