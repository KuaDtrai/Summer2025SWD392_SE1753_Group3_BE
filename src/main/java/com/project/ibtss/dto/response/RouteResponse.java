package com.project.ibtss.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteResponse {
    Integer id;
    String name;
    StationResponse departureStation;
    StationResponse destinationStation;
    Integer distance;
    LocalTime estimatedTime;
}
