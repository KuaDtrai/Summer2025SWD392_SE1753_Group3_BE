package com.project.ibtss.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusRequest {
    @NotBlank(message = "License plate is required")
    String licensePlate;

    @NotNull(message = "Seat count is required")
    @Min(value = 1, message = "Seat count must be greater than 0")
    Integer seatCount;

    @NotBlank(message = "Bus type is required")
    String busType;

    String status;
}
