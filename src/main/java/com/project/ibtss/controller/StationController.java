package com.project.ibtss.controller;

import com.project.ibtss.dto.request.StationRequest;
import com.project.ibtss.dto.request.UpdateStationRequest;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.StationResponse;
import com.project.ibtss.model.Stations;
import com.project.ibtss.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/station")
public class StationController {
    private final StationService stationService;

    @GetMapping("/active")
    public ApiResponse<List<StationResponse>> getActiveStation(){
        return ApiResponse.<List<StationResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(stationService.getActiveStation()).build();
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('admin:read')")
    public ApiResponse<List<StationResponse>> getStations() {
        return ApiResponse.<List<StationResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(stationService.getAllStation()).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ApiResponse<StationResponse> getStationById(@PathVariable Integer id) {
        return ApiResponse.<StationResponse>builder().code(HttpStatus.OK.value()).message("").data(stationService.getStationById(id)).build();
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('admin:create')")
    public ApiResponse<StationResponse> createStation(@RequestBody StationRequest stationRequest) {
        return ApiResponse.<StationResponse>builder().code(HttpStatus.OK.value()).message("").data(stationService.createStation(stationRequest)).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ApiResponse<StationResponse> updateStation(@PathVariable Integer id, @RequestBody UpdateStationRequest stationRequest) {
        return ApiResponse.<StationResponse>builder().code(HttpStatus.OK.value()).message("").data(stationService.updateStation(id, stationRequest)).build();
    }

    @PutMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ApiResponse<StationResponse> deleteStation(@PathVariable Integer id) {
        return ApiResponse.<StationResponse>builder().code(HttpStatus.OK.value()).message("").data(stationService.deleteStationById(id)).build();
    }
}
