package com.project.ibtss.controller;

import com.project.ibtss.dto.request.BusRequest;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.BusResponse;
import com.project.ibtss.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/buses")
public class BusController {
    @Autowired
    private BusService busService;

    @PostMapping
    public ResponseEntity<ApiResponse<BusResponse>> createBus(@Valid @RequestBody BusRequest busRequest) {
        try {
            BusResponse createdBus = busService.createBus(busRequest);
            return ResponseEntity.ok(ApiResponse.<BusResponse>builder()
                    .code(200)
                    .message("Bus created successfully")
                    .data(createdBus)
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.<BusResponse>builder()
                    .code(400)
                    .message(e.getMessage())
                    .data(null)
                    .build());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BusResponse>> getBusById(@PathVariable Integer id) {
        try {
            BusResponse bus = busService.getBusById(id);
            return ResponseEntity.ok(ApiResponse.<BusResponse>builder()
                    .code(200)
                    .message("Bus retrieved successfully")
                    .data(bus)
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(ApiResponse.<BusResponse>builder()
                    .code(404)
                    .message(e.getMessage())
                    .data(null)
                    .build());
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BusResponse>>> getAllBuses() {
        List<BusResponse> buses = busService.getAllBuses();
        return ResponseEntity.ok(ApiResponse.<List<BusResponse>>builder()
                .code(200)
                .message("Buses retrieved successfully")
                .data(buses)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BusResponse>> updateBus(@PathVariable Integer id, @Valid @RequestBody BusRequest busRequest) {
        try {
            BusResponse updatedBus = busService.updateBus(id, busRequest);
            return ResponseEntity.ok(ApiResponse.<BusResponse>builder()
                    .code(200)
                    .message("Bus updated successfully")
                    .data(updatedBus)
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.<BusResponse>builder()
                    .code(400)
                    .message(e.getMessage())
                    .data(null)
                    .build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBus(@PathVariable Integer id) {
        try {
            busService.deleteBus(id);
            return ResponseEntity.ok(ApiResponse.<Void>builder()
                    .code(200)
                    .message("Bus deleted successfully")
                    .data(null)
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.<Void>builder()
                    .code(400)
                    .message(e.getMessage())
                    .data(null)
                    .build());
        }
    }
}