package com.project.ibtss.dto.request;

import jakarta.validation.Valid;
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
    String name;
    @Valid
    Integer departureStationId;
    @Valid
    Integer destinationStationId;
    Integer distanceKm;
    LocalTime estimatedTime;
    String status;
}
