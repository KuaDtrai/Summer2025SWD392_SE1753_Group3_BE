package com.project.ibtss.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TripResponse {
    Integer id;
    String routeName;
    String busPlate;
    String driverName;
    LocalDateTime departureTime;
    LocalDateTime arrivalTime;
    Float price;
    String status;
}