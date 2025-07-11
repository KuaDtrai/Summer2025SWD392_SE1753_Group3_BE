package com.project.ibtss.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketSegmentResponse {
    int tripId;
    String seatCode;
    float price;
    LocalDateTime departureTime;
    String fromStationName;
    String toStationName;
}
