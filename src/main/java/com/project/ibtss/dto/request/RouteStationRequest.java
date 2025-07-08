package com.project.ibtss.dto.request;

import jakarta.validation.Valid;
import lombok.Getter;

@Getter
public class RouteStationRequest {
    @Valid
    private Integer routeId;
    @Valid
    private Integer stationId;
    @Valid
    private Integer stationOrder;
}
