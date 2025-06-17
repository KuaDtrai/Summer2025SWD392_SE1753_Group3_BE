package com.project.ibtss.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
public class TicketSegmentResponse {
    private Integer id;
    private String journeyCode;
    private Integer legOrder;
    private TripResponse trip;
    private String seatCode;
    private RouteStationResponse fromRouteStation;
    private RouteStationResponse toRouteStation;
    private Float price;
}
