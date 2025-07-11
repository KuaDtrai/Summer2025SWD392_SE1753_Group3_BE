package com.project.ibtss.service;

import com.project.ibtss.dto.response.RouteStationSelectResponse;

import java.util.List;

public interface RouteStationService {
    List<RouteStationSelectResponse> getAllRouteStationSelectByTrip(int tripId);
    List<RouteStationSelectResponse> getAllRouteStationSelectByRoute(int routeId);
}
