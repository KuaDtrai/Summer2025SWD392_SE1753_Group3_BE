package com.project.ibtss.service.service_implement;

import com.project.ibtss.dto.request.AdjusmentPaymentRequest;
import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.service.PayOSService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

@Service
@RequiredArgsConstructor
public class PayOSServiceImpl implements PayOSService {
    private final PayOS payOS;

    @Override
    public CheckoutResponseData createPaymentLink(PaymentSeatRequest request, long orderCode, Integer paymentId, String description) throws Exception {
        ItemData item = ItemData.builder()
                .name("Payment: " + paymentId)
                .price(request.getTotalPrice().intValue())
                .quantity(request.getSeatIds().size())
                .build();

        PaymentData paymentData = PaymentData.builder()
                .orderCode(paymentId.longValue())
                .amount(request.getTotalPrice().intValue())
                .description(description)
                .returnUrl("http://localhost:5173/payment-callback")
                .cancelUrl("http://localhost:5173/payment-callback")
                .item(item)
                .build();
            return payOS.createPaymentLink(paymentData);
    }

    @Override
    public CheckoutResponseData createAdjusmentPaymentTicket(AdjusmentPaymentRequest request, long orderCode, Integer paymentId, String description) throws Exception {
        ItemData item = ItemData.builder()
                .name("Payment: " + paymentId)
                .price(request.getTotalPrice().intValue())
                .quantity(1)
                .build();

        PaymentData paymentData = PaymentData.builder()
                .orderCode(paymentId.longValue())
                .amount(request.getTotalPrice().intValue())
                .description(description)
                .returnUrl("http://localhost:5173/payment-callback")
                .cancelUrl("http://localhost:5173/payment-callback")
                .item(item)
                .build();
        return payOS.createPaymentLink(paymentData);
    }


}
