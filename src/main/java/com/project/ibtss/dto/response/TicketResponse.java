package com.project.ibtss.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketResponse {
    private Integer ticketId;
    private String journeyCode;
    private String ticketOwnerName;
    private String ticketOwnerPhone;
    private LocalDateTime bookingTime;
    private String status;
    TicketSegmentResponse ticketSegmentResponse;
}
