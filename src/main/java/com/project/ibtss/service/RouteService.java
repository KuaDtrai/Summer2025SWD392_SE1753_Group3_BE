package com.project.ibtss.service;

import com.project.ibtss.dto.request.RouteRequest;
import com.project.ibtss.dto.response.RouteResponse;

import java.util.List;

public interface RouteService {
    RouteResponse getRouteById(Integer id);
    List<RouteResponse> getAllRoute();
    RouteResponse createRoute(RouteRequest routeRequest);
    RouteResponse updateRoute(Integer id, RouteRequest routeRequest);
    RouteResponse deleteRoute(Integer id);
}
