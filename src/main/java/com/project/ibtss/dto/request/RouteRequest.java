package com.project.ibtss.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class RouteRequest {
    @Valid
    @Size(max = 50)
    String name;
    @Valid
    Integer departureStationId;
    @Valid
    Integer destinationStationId;
    Integer distanceKm;
    LocalTime estimatedTime;
}
