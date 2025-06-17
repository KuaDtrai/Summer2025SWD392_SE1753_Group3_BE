package com.project.ibtss.controller;

import com.project.ibtss.dto.request.RouteRequest;
import com.project.ibtss.dto.request.StationRequest;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.RouteResponse;
import com.project.ibtss.dto.response.StationResponse;
import com.project.ibtss.mapper.RouteMapper;
import com.project.ibtss.model.Routes;
import com.project.ibtss.repository.RouteRepository;
import com.project.ibtss.service.RouteService;
import com.project.ibtss.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/route")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('admin:read')")
    public ApiResponse<List<RouteResponse>> getRoutes() {
        return ApiResponse.<List<RouteResponse>>builder()
                .code(HttpStatus.OK.value()).message("")
                .data(routeService.getAllRoute()).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ApiResponse<RouteResponse> getRouteById(@PathVariable Integer id) {
        return ApiResponse.<RouteResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(routeService.getRouteById(id)).build();
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('admin:create')")
    public ApiResponse<RouteResponse> createRoute(@RequestBody RouteRequest routeRequest) {
        return ApiResponse.<RouteResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(routeService.createRoute(routeRequest)).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ApiResponse<RouteResponse> updateRoute(@PathVariable Integer id, @RequestBody RouteRequest routeRequest) {
        return ApiResponse.<RouteResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(routeService.updateRoute(id, routeRequest)).build();
    }

    @PutMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ApiResponse<RouteResponse> deleteRoute(@PathVariable Integer id) {
        return ApiResponse.<RouteResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(routeService.deleteRoute(id)).build();
    }
}
