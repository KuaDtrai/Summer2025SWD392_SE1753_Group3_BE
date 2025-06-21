package com.project.ibtss.service_implement;

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
    public CheckoutResponseData createPaymentLink(PaymentSeatRequest request, long orderCode, int paymentId) {
        ItemData item = ItemData.builder()
                .name("Payment: " + paymentId)
                .price(request.getTotalPrice().intValue()) // PayOS dùng int
                .quantity(request.getSeatIds().size())
                .build();

        PaymentData paymentData = PaymentData.builder()
                .orderCode(orderCode)
                .amount(request.getTotalPrice().intValue())
                .description("Thanh toán vé xe")
                .returnUrl("http://localhost:5173/")
                .cancelUrl("http://localhost:5173/")
                .item(item)
                .build();

        try {
            return payOS.createPaymentLink(paymentData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
