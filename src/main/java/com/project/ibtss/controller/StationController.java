package com.project.ibtss.controller;

import com.project.ibtss.dto.request.StationRequest;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.StationResponse;
import com.project.ibtss.model.Stations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/station")
public class StationController {
    @GetMapping("")
    public ApiResponse getStations() {
        return ApiResponse.builder().code(HttpStatus.OK.value()).message("").data(new StationResponse()).build();
    }

    @GetMapping("/{id}")
    public ApiResponse getStationById(@PathVariable Integer id) {
        return ApiResponse.builder().code(HttpStatus.OK.value()).message("").data(new StationResponse()).build();
    }

    @PostMapping("")
    public ApiResponse createStation(@RequestBody StationRequest stationRequest) {
        return ApiResponse.builder().code(HttpStatus.OK.value()).message("").data(new StationResponse()).build();
    }

    @PutMapping("/{id}")
    public ApiResponse updateStation(@PathVariable Integer id, @RequestBody StationRequest stationRequest) {
        return ApiResponse.builder().code(HttpStatus.OK.value()).message("").data(new StationResponse()).build();
    }

    @PutMapping("/delete/{id}")
    public ApiResponse deleteStation(@PathVariable Integer id) {
        return ApiResponse.builder().code(HttpStatus.OK.value()).message("").data(new StationResponse()).build();
    }
}
