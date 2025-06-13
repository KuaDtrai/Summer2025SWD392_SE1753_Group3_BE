package com.project.ibtss.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusResponse {
    Integer id;
    String licensePlate;
    Integer seatCount;
    String busType;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}