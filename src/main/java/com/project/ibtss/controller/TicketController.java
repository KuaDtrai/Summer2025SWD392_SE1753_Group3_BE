package com.project.ibtss.controller;

import com.project.ibtss.dto.request.TicketRequest;
import com.project.ibtss.dto.response.*;
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
@RequestMapping("/ticket")
public class TicketController {
    private TicketResponse getTicketResponse() {
        return new TicketResponse(1, "", "Kien Ho", "Kien Ho", "0912345678", LocalDateTime.now(), "", "Khánh");
    };

    @GetMapping("/{id}")
    public ApiResponse<TicketResponse> getTicket(@PathVariable Integer id) {
        return ApiResponse.<TicketResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully get ticket")
                .data(getTicketResponse())
                .build();
    }

    @GetMapping
    public ApiResponse<List<TicketResponse>> getTicket() {
        List<TicketResponse> ticketResponseList = new ArrayList<>();
        ticketResponseList.add(getTicketResponse());
        ticketResponseList.add(getTicketResponse());
        return ApiResponse.<List<TicketResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully get ticket")
                .data(ticketResponseList)
                .build();
    }

    @PostMapping
    public ApiResponse<TicketResponse> createTicket(@RequestBody TicketRequest ticketRequest) {
        return ApiResponse.<TicketResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully get ticket")
                .data(getTicketResponse())
                .build();
    }
}
