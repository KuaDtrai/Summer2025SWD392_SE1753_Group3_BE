package com.project.ibtss.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TicketRequest {
    private String ticketOwnerName;
    private String ticketOwnerPhone;
}
