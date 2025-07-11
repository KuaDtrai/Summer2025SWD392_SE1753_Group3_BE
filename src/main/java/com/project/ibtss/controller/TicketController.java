package com.project.ibtss.controller;

import com.project.ibtss.dto.request.ChangeTicketRequest;
import com.project.ibtss.dto.request.TicketRequest;
import com.project.ibtss.dto.response.*;
import com.project.ibtss.enums.Position;
import com.project.ibtss.enums.Role;
import com.project.ibtss.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ApiResponse<Page<TicketResponse>> getAllTicketByAccountId(
            @PathVariable Integer accountId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return ApiResponse.<Page<TicketResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(ticketService.getAllTicketByAccountId(accountId, page, size))
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

    @PutMapping("/{ticketId}")
    ApiResponse<String> cancelTicket(@PathVariable Integer ticketId){
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(ticketService.cancelTicket(ticketId))
                .build();
    }
}
