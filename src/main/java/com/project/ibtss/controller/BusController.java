package com.project.ibtss.controller;

import com.project.ibtss.dto.request.BusRequest;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.BusResponse;
import com.project.ibtss.service.BusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buses")
@RequiredArgsConstructor
public class BusController {
    private final BusService busService;

    @GetMapping
    public ApiResponse<List<BusResponse>> getAllBuses(@RequestParam(value = "licensePlate", required = false) String licensePlate) {
        List<BusResponse> buses = (licensePlate != null && !licensePlate.isEmpty()) // Check if license plate is provided
                ? busService.searchByLicensePlate(licensePlate) // Search by license plate if provided
                : busService.getAllBuses(); // Otherwise, get all buses
        return ApiResponse.<List<BusResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .data(buses)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BusResponse> getBusById(@PathVariable Integer id) {
        return ApiResponse.<BusResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .data(busService.getBusById(id))
                .build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ApiResponse<BusResponse> createBus(@Valid @RequestBody BusRequest request) {
        return ApiResponse.<BusResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Created successfully")
                .data(busService.createBus(request))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ApiResponse<BusResponse> updateBus(@PathVariable Integer id, @Valid @RequestBody BusRequest request) {
        return ApiResponse.<BusResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Updated successfully")
                .data(busService.updateBus(id, request))
                .build();
    }

    @PutMapping("/active/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ApiResponse<Void> deleteBus(@PathVariable Integer id) {
        busService.deleteBus(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Deleted successfully")
                .build();
    }
}
