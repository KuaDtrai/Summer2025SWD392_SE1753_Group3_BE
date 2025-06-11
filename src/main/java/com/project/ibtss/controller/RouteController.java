package com.project.ibtss.controller;

import com.project.ibtss.dto.request.RouteRequest;
import com.project.ibtss.dto.request.StationRequest;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.RouteResponse;
import com.project.ibtss.dto.response.StationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/route")
public class RouteController {
    @GetMapping("")
    public ApiResponse getRoutes() {
        return ApiResponse.builder().code(HttpStatus.OK.value()).message("").data(new RouteResponse()).build();
    }

    @GetMapping("/{id}")
    public ApiResponse getRouteById(@PathVariable Integer id) {
        return ApiResponse.builder().code(HttpStatus.OK.value()).message("").data(new RouteResponse()).build();
    }

    @PostMapping("")
    public ApiResponse createRoute(@RequestBody RouteRequest routeRequest) {
        return ApiResponse.builder().code(HttpStatus.OK.value()).message("").data(new RouteResponse()).build();
    }

    @PutMapping("/{id}")
    public ApiResponse updateRoute(@PathVariable Integer id, @RequestBody RouteRequest routeRequest) {
        return ApiResponse.builder().code(HttpStatus.OK.value()).message("").data(new RouteResponse()).build();
    }

    @PutMapping("/delete/{id}")
    public ApiResponse deleteRoute(@PathVariable Integer id) {
        return ApiResponse.builder().code(HttpStatus.OK.value()).message("").data(new RouteResponse()).build();
    }
}
