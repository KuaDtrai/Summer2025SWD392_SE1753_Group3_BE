package com.project.ibtss.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
public class RouteStationResponse {
    private Integer id;
    private RouteResponse route;
    private StationResponse station;
    private Integer stationOrder;
}
