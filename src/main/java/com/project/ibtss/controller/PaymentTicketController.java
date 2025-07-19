package com.project.ibtss.controller;

import com.project.ibtss.dto.request.ChangeTicketRequest;
import com.project.ibtss.dto.request.PaymentConfirmRequest;
import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.PaymentProcessResponse;
import com.project.ibtss.service.PaymentTicketService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket-payment")
public class PaymentTicketController {

    @Autowired
    private PaymentTicketService paymentTicketService;

    @PostMapping
    ApiResponse<PaymentProcessResponse> createPaymentTicket(@RequestBody PaymentSeatRequest request) throws Exception {
        return ApiResponse.<PaymentProcessResponse>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(paymentTicketService.createPaymentTicket(request))
                .build();
    }

    @PostMapping("/confirm")
    ApiResponse<String> confirmPaymentTicket(@RequestBody PaymentConfirmRequest request) throws MessagingException {
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(paymentTicketService.confirmPaymentTicket(request))
                .build();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('customer:update')")
    ApiResponse<PaymentProcessResponse> changeTicket(@RequestBody ChangeTicketRequest request) throws Exception {
        return ApiResponse.<PaymentProcessResponse>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(paymentTicketService.changeTicket(request))
                .build();
    }


}
