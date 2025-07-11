package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.enums.PaymentMethod;
import com.project.ibtss.enums.PaymentStatus;
import com.project.ibtss.model.Payment;
import com.project.ibtss.repository.PaymentRepository;
import com.project.ibtss.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public Payment createPayment(PaymentSeatRequest request) {
        Payment payment = Payment.builder()
                .paymentMethod(PaymentMethod.PAYOS)
                .totalAmount(request.getTotalPrice())
                .createdDate(LocalDateTime.now())
                .status(PaymentStatus.PENDING)
                .build();
        return paymentRepository.save(payment);
    }

    @Override
    public Payment createPaymentForSubPay(Float totalAmount) {
        Payment payment = Payment.builder()
                .paymentMethod(PaymentMethod.PAYOS)
                .totalAmount(totalAmount)
                .createdDate(LocalDateTime.now())
                .status(PaymentStatus.PENDING)
                .build();
        return paymentRepository.save(payment);
    }
}
