package com.project.ibtss.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TicketResponse {
    private Integer ticketId;
    private String journeyCode;
    private String customerName;
    private String ticketOwnerName;
    private String ticketOwnerPhone;
    private LocalDateTime bookingTime;
    private String status;
    private String staffName;
}
