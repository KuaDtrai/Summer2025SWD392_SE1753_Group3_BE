package com.project.ibtss.controller;

import com.project.ibtss.dto.request.RouteStationRequest;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.RouteResponse;
import com.project.ibtss.dto.response.RouteStationResponse;
import com.project.ibtss.dto.response.StationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/routeStation")
public class RouteStationController {
    private RouteStationResponse getRouteStationResponse() {
        RouteResponse routeResponse = new RouteResponse();
        StationResponse stationResponse = new StationResponse();
        return new RouteStationResponse(1, routeResponse, stationResponse, 1);
    }

    @PostMapping
    public ApiResponse<RouteStationResponse> createRouteStation(@RequestBody RouteStationRequest routeStationRequest) {
        return ApiResponse.<RouteStationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(getRouteStationResponse())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<RouteStationResponse> getRouteStation(@PathVariable Integer id) {
        return ApiResponse.<RouteStationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(getRouteStationResponse())
                .build();
    }

    @GetMapping
    public ApiResponse<List<RouteStationResponse>> getRouteStations() {
        List<RouteStationResponse> routeStationResponseList = new ArrayList<>();
        routeStationResponseList.add(getRouteStationResponse());
        routeStationResponseList.add(getRouteStationResponse());
        return ApiResponse.<List<RouteStationResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(routeStationResponseList)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<RouteStationResponse> updateRouteStation(@PathVariable Integer id, @RequestBody RouteStationRequest routeStationRequest) {
        return ApiResponse.<RouteStationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(getRouteStationResponse())
                .build();
    }
}
