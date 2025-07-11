package com.project.ibtss.dto.response;

import com.project.ibtss.model.Stations;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteResponse {
    Integer id;
    String name;
    StationResponse departureStation;
    StationResponse destinationStation;
    List<Stations> listStation;
    Integer distanceKm;
    LocalTime estimatedTime;
    String status;
}
