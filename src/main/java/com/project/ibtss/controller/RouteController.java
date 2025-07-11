package com.project.ibtss.controller;

import com.project.ibtss.dto.request.CreateRouteRequest;
import com.project.ibtss.dto.request.RouteUpdateRequest;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.RouteResponse;
import com.project.ibtss.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/active")
    public ApiResponse<Page<RouteResponse>> getRoutesActive(@RequestParam(defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "") String search) {
        return ApiResponse.<Page<RouteResponse>>builder()
                .code(HttpStatus.OK.value()).message("")
                .data(routeService.getAllRouteActive(page, search)).build();
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
    public ApiResponse<RouteResponse> createRoute(@RequestBody CreateRouteRequest createRouteRequest) {
        return ApiResponse.<RouteResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(routeService.createRoute(createRouteRequest)).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ApiResponse<RouteResponse> updateRoute(@PathVariable Integer id, @RequestBody RouteUpdateRequest routeRequest) {
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
