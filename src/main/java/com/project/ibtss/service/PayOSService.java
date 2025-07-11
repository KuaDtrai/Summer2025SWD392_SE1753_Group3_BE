package com.project.ibtss.service;

import com.project.ibtss.dto.request.AdjusmentPaymentRequest;
import com.project.ibtss.dto.request.PaymentSeatRequest;
import vn.payos.type.CheckoutResponseData;

public interface PayOSService {
    CheckoutResponseData createPaymentLink(PaymentSeatRequest request, long orderCode, Integer paymentId, String description) throws Exception;
    CheckoutResponseData createAdjusmentPaymentTicket(AdjusmentPaymentRequest request, long orderCode, Integer paymentId, String description) throws Exception;
}
