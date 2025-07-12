package com.project.ibtss.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouteUpdateRequest {
    @Valid
    @Size(max = 50)
    String name;
    @Valid
    Integer departureStationId;
    @Valid
    Integer destinationStationId;
    Integer distanceKm;
    LocalTime estimatedTime;
    String status;
}
