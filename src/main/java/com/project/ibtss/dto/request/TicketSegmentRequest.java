package com.project.ibtss.dto.request;

import lombok.Getter;

@Getter
public class TicketSegmentRequest {
    private String journeyTicketId;
    private Integer legOrder;
    private Integer tripId;
    private Integer seatTd;
    private Integer fromRouteStationId;
    private Integer toRouteStationId;
    private Float price;
}
