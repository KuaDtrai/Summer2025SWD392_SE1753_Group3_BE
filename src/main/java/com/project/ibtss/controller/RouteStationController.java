package com.project.ibtss.controller;

import com.project.ibtss.dto.request.RouteStationRequest;
import com.project.ibtss.dto.response.*;
import com.project.ibtss.service.RouteStationService;
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

    private final RouteStationService routeStationService;

//    private RouteStationResponse getRouteStationResponse() {
//        RouteResponse routeResponse = new RouteResponse();
//        StationResponse stationResponse = new StationResponse();
//        return new RouteStationResponse(1, routeResponse, stationResponse, 1);
//    }
//
//    @PostMapping
//    public ApiResponse<RouteStationResponse> createRouteStation(@RequestBody RouteStationRequest routeStationRequest) {
//        return ApiResponse.<RouteStationResponse>builder()
//                .code(HttpStatus.OK.value())
//                .message("")
//                .data(getRouteStationResponse())
//                .build();
//    }
//
//    @GetMapping("/{id}")
//    public ApiResponse<RouteStationResponse> getRouteStation(@PathVariable Integer id) {
//        return ApiResponse.<RouteStationResponse>builder()
//                .code(HttpStatus.OK.value())
//                .message("")
//                .data(getRouteStationResponse())
//                .build();
//    }
//
//    @GetMapping
//    public ApiResponse<List<RouteStationResponse>> getRouteStations() {
//        List<RouteStationResponse> routeStationResponseList = new ArrayList<>();
//        routeStationResponseList.add(getRouteStationResponse());
//        routeStationResponseList.add(getRouteStationResponse());
//        return ApiResponse.<List<RouteStationResponse>>builder()
//                .code(HttpStatus.OK.value())
//                .message("")
//                .data(routeStationResponseList)
//                .build();
//    }
//
//    @PutMapping("/{id}")
//    public ApiResponse<RouteStationResponse> updateRouteStation(@PathVariable Integer id, @RequestBody RouteStationRequest routeStationRequest) {
//        return ApiResponse.<RouteStationResponse>builder()
//                .code(HttpStatus.OK.value())
//                .message("")
//                .data(getRouteStationResponse())
//                .build();
//    }

    //trip id
    @GetMapping("/trip/{id}")
    public ApiResponse<List<RouteStationSelectResponse>> getAllRouteStationSelectByTrip(@PathVariable Integer id) {
        return ApiResponse.<List<RouteStationSelectResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(routeStationService.getAllRouteStationSelectByTrip(id))
                .build();
    }

    @GetMapping("/route/{id}")
    public ApiResponse<List<RouteStationSelectResponse>> getAllRouteStationSelectByRoute(@PathVariable Integer id) {
        return ApiResponse.<List<RouteStationSelectResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(routeStationService.getAllRouteStationSelectByRoute(id))
                .build();
    }
}
