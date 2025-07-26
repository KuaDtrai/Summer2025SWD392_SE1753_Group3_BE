package com.project.ibtss.controller;

import com.project.ibtss.dto.request.StationRequest;
import com.project.ibtss.dto.request.UpdateStationRequest;
import com.project.ibtss.dto.request.UpdateStatusStation;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.StationResponse;
import com.project.ibtss.model.Stations;
import com.project.ibtss.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
                .data(stationService.getActiveStation())
                .build();
    }

    @GetMapping("/active-paginated")
    public ApiResponse<Page<StationResponse>> getActiveStationsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.<Page<StationResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Fetched successfully")
                .data(stationService.getStationsByStatus(PageRequest.of(page, size)))
                .build();
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('staff:read')")
    public ApiResponse<Page<StationResponse>> getStations(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return ApiResponse.<Page<StationResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(stationService.getAllStation(pageable)).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('staff:read')")
    public ApiResponse<StationResponse> getStationById(@PathVariable Integer id) {
        return ApiResponse.<StationResponse>builder().code(HttpStatus.OK.value()).message("").data(stationService.getStationById(id)).build();
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('staff:create')")
    public ApiResponse<StationResponse> createStation(@RequestBody StationRequest stationRequest) {
        return ApiResponse.<StationResponse>builder().code(HttpStatus.OK.value()).message("").data(stationService.createStation(stationRequest)).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('staff:update')")
    public ApiResponse<StationResponse> updateStation(@PathVariable Integer id, @RequestBody UpdateStationRequest stationRequest) {
        return ApiResponse.<StationResponse>builder().code(HttpStatus.OK.value()).message("").data(stationService.updateStation(id, stationRequest)).build();
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('staff:delete')")
    public ApiResponse<StationResponse> updateStatusStation(@PathVariable Integer id, @RequestBody UpdateStatusStation statusStation) {
        return ApiResponse.<StationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("")
                .data(stationService.updateStatusStation(id, statusStation))
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<Stations>> searchStations(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search
    ) {
        return ApiResponse.<List<Stations>>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data((stationService.searchStations(search, page, size)))
                .build();
    }
}
