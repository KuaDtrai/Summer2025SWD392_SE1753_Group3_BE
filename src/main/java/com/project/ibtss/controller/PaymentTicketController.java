package com.project.ibtss.controller;

import com.project.ibtss.dto.request.PaymentConfirmRequest;
import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.CreatePaymentResponse;
import com.project.ibtss.service.PaymentTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket-payment")
public class PaymentTicketController {

    @Autowired
    private PaymentTicketService paymentTicketService;

    @PostMapping
    ApiResponse<CreatePaymentResponse> createPaymentTicket(@RequestBody PaymentSeatRequest request) throws Exception {
        return ApiResponse.<CreatePaymentResponse>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(paymentTicketService.createPaymentTicket(request))
                .build();
    }

    @PostMapping("/confirm")
    ApiResponse<CreatePaymentResponse> confirmPaymentTicket(@RequestBody PaymentConfirmRequest request){
        return ApiResponse.<CreatePaymentResponse>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(paymentTicketService.confirmPaymentTicket(request))
                .build();
    }
}
