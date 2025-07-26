package com.project.ibtss.service;

import com.project.ibtss.dto.request.CreateRouteRequest;
import com.project.ibtss.dto.request.RouteUpdateRequest;
import com.project.ibtss.dto.request.UpdateRouteStatus;
import com.project.ibtss.dto.response.RouteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RouteService {
    RouteResponse getRouteById(Integer id);
    Page<RouteResponse> getAllRoute(Pageable pageable);
    RouteResponse createRoute(CreateRouteRequest createRouteRequest);
    RouteResponse updateRoute(Integer id, RouteUpdateRequest routeRequest);
    RouteResponse updateRouteStatus(Integer id, UpdateRouteStatus routeRequest);

    RouteResponse deleteRoute(Integer id);

    Page<RouteResponse> getAllRouteActive(int page, String search);
}
