package com.project.ibtss.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationRequest {
    @NotBlank(message = "Station name is required")
    String name;

    @NotBlank(message = "Address is required")
    String address;
}