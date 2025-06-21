package com.project.ibtss.controller;

import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.PaymentResponse;
import com.project.ibtss.enums.PaymentMethod;
import com.project.ibtss.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
//    private final PaymentService paymentService;
    @GetMapping
    public ApiResponse<PaymentResponse> getPayment() {
        return ApiResponse.<PaymentResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(new PaymentResponse(1, PaymentMethod.PAYOS, 10.0f, LocalDateTime.now()))
                .build();
    }
}
