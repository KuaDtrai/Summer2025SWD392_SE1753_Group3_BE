package com.project.ibtss.controller;

import com.project.ibtss.dto.request.TicketRequest;
import com.project.ibtss.dto.response.*;
import com.project.ibtss.enums.Position;
import com.project.ibtss.enums.Role;
import com.project.ibtss.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/{accountId}")
    ApiResponse<List<TicketResponse>> getAllTicketByAccountId(@PathVariable int accountId) {
        return ApiResponse.<List<TicketResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(ticketService.getAllTicketByAccountId(accountId))
                .build();
    }

    @GetMapping("/search/{code}")
    ApiResponse<TicketResponse> searchTicketByCode(@PathVariable String code) {
        return ApiResponse.<TicketResponse>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(ticketService.searchTicketByCode(code))
                .build();
    }

    @GetMapping("/id/{ticketId}")
    ApiResponse<TicketResponse> searchTicketById(@PathVariable Integer ticketId) {
        return ApiResponse.<TicketResponse>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(ticketService.searchTicketById(ticketId))
                .build();
    }

    @PutMapping("/ticketId")
    ApiResponse<String> cancelTicket(Integer ticketId){
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(ticketService.cancelTicket(ticketId))
                .build();
    }
}
