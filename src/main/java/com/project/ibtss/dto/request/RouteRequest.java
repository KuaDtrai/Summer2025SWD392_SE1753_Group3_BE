package com.project.ibtss.dto.request;

import jakarta.validation.Valid;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class RouteRequest {
    @Valid
    String name;
    @Valid
    Integer departureStationId;
    @Valid
    Integer destinationStationId;
    Integer distanceKm;
    LocalTime estimatedTime;
}
