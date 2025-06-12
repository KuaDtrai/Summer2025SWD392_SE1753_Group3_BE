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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/route")
@RequiredArgsConstructor
public class RouteController {
    private final RouteRepository routeRepository;
    private final RouteService routeService;
    private final RouteMapper routeMapper;

    @GetMapping("")
    public ApiResponse<List<RouteResponse>> getRoutes() {
        return ApiResponse.<List<RouteResponse>>builder()
                .code(HttpStatus.OK.value()).message("")
                .data(routeService.getAllRoute()).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<RouteResponse> getRouteById(@PathVariable Integer id) {
        return ApiResponse.<RouteResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(routeService.getRouteById(id)).build();
    }

    @PostMapping("")
    public ApiResponse<RouteResponse> createRoute(@RequestBody RouteRequest routeRequest) {
        return ApiResponse.<RouteResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(routeService.createRoute(routeRequest)).build();
    }

    @PutMapping("/{id}")
    public ApiResponse<RouteResponse> updateRoute(@PathVariable Integer id, @RequestBody RouteRequest routeRequest) {
        return ApiResponse.<RouteResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(routeService.updateRoute(id, routeRequest)).build();
    }

    @PutMapping("/delete/{id}")
    public ApiResponse<RouteResponse> deleteRoute(@PathVariable Integer id) {
        return ApiResponse.<RouteResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(routeService.delete(id)).build();
    }
}
