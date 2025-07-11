package com.project.ibtss.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RouteStationSelectResponse {
    int stationId;
    String stationName;
    String address;
    int stationOrder;
}
