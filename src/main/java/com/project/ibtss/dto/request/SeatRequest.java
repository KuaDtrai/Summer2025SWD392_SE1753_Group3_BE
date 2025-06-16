package com.project.ibtss.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatRequest {
    @NotNull(message = "Bus ID is required")
    Integer busId;

    @NotBlank(message = "Seat code is required")
    String seatCode;
}
