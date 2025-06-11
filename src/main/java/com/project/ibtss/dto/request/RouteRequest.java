package com.project.ibtss.dto.request;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class RouteRequest {
    String name;
    Integer departureStationId;
    Integer destinationStationId;
    Integer distance;
    LocalTime estimatedTime;
}
