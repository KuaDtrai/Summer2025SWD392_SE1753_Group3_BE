package com.project.ibtss.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TripRequest {
    @NotNull(message = "Route ID is required")
    Integer routeId;

    @NotNull(message = "Bus ID is required")
    Integer busId;

    @NotNull(message = "Driver ID is required")
    Integer driverId;

    @NotNull(message = "Departure time is required")
    @Future(message = "Departure time must be in the future")
    LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    @Future(message = "Arrival time must be in the future")
    LocalDateTime arrivalTime;

    @NotNull(message = "Price is required")
    Float price;

    String status;
}