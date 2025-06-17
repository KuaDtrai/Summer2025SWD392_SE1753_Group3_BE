package com.project.ibtss.controller;

import com.project.ibtss.dto.request.TicketPayRequest;
import com.project.ibtss.dto.response.*;
import com.project.ibtss.enums.PaymentMethod;
import com.project.ibtss.enums.Position;
import com.project.ibtss.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticketPay")
public class TicketPayController {
    private TicketPayResponse getTicketPayResponse() {
        PaymentResponse paymentResponse = new PaymentResponse(1, PaymentMethod.PAYOS, 10f, LocalDateTime.now());
        return new  TicketPayResponse(1, paymentResponse, "Journey code", 10f);
    };

    @GetMapping("/{id}")
    public ApiResponse<TicketPayResponse> getTicketSegmentResponse() {
        return ApiResponse.<TicketPayResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(getTicketPayResponse())
                .build();
    }

    @GetMapping
    public ApiResponse<List<TicketPayResponse>> getTicketPaysResponse() {
        List<TicketPayResponse> ticketPayResponses = new ArrayList<TicketPayResponse>();
        ticketPayResponses.add(getTicketPayResponse());
        ticketPayResponses.add(getTicketPayResponse());
        return ApiResponse.<List<TicketPayResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(ticketPayResponses)
                .build();
    }

    @PostMapping
    public ApiResponse<TicketPayResponse> createTicketPayResponse(@RequestBody TicketPayRequest ticketPayRequest) {
        return ApiResponse.<TicketPayResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(getTicketPayResponse())
                .build();
    }

    @PutMapping
    public ApiResponse<TicketPayResponse> updateTicketPayResponse(@RequestBody TicketPayRequest ticketPayRequest) {
        return ApiResponse.<TicketPayResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(getTicketPayResponse())
                .build();
    }
}
