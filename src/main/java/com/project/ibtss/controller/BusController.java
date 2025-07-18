package com.project.ibtss.controller;

import com.project.ibtss.dto.request.BusRequest;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.BusResponse;
import com.project.ibtss.service.BusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/buses")
@RequiredArgsConstructor
public class BusController {
    private final BusService busService;

    @GetMapping
    @PreAuthorize("hasAuthority('staff:read')")
    public ApiResponse<Page<BusResponse>> getAllBuses(
            @PageableDefault(size = 10, page = 0) Pageable pageable) {

        Page<BusResponse> buses = busService.getAllBuses(pageable);
        return ApiResponse.<Page<BusResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .data(buses)
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('staff:read')")
    public ApiResponse<BusResponse> getBusById(@PathVariable Integer id) {
        return ApiResponse.<BusResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .data(busService.getBusById(id))
                .build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('staff:create')")
    public ApiResponse<BusResponse> createBus(@Valid @RequestBody BusRequest request) {
        return ApiResponse.<BusResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Created successfully")
                .data(busService.createBus(request))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('staff:update')")
    public ApiResponse<BusResponse> updateBus(@PathVariable Integer id, @Valid @RequestBody BusRequest request) {
        return ApiResponse.<BusResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Updated successfully")
                .data(busService.updateBus(id, request))
                .build();
    }

    @PutMapping("/active/{id}")
    @PreAuthorize("hasAuthority('staff:delete')")
    public ApiResponse<BusResponse> deleteBus(@PathVariable Integer id) {

        return ApiResponse.<BusResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Deleted successfully")
                .data(busService.setBusActive(id))
                .build();
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('staff:read')")
    public ApiResponse<Page<BusResponse>> searchBuses(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "") String search
    ) {
        return ApiResponse.<Page<BusResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(busService.searchBuses(page, search))
                .build();
    }

    @GetMapping("/available")
    public ApiResponse<Page<BusResponse>> getAvailableBuses(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalTime,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String search
    ) {
        final int DEFAULT_PAGE_SIZE = 10;
        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE);
        return ApiResponse.<Page<BusResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .data(busService.getAvailableBuses(departureTime, arrivalTime, pageable, search))
                .build();
    }
}
