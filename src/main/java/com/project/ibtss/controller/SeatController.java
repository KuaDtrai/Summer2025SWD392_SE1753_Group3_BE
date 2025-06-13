package com.project.ibtss.controller;

import com.project.ibtss.dto.request.SeatRequest;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.SeatResponse;
import com.project.ibtss.service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ApiResponse<SeatResponse> create(@Valid @RequestBody SeatRequest request) {
        return ApiResponse.<SeatResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Seat created")
                .data(seatService.createSeat(request))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ApiResponse<SeatResponse> update(@PathVariable Integer id, @Valid @RequestBody SeatRequest request) {
        return ApiResponse.<SeatResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Seat updated")
                .data(seatService.updateSeat(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        seatService.deleteSeat(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Seat deleted")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<SeatResponse> getById(@PathVariable Integer id) {
        return ApiResponse.<SeatResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .data(seatService.getSeatById(id))
                .build();
    }

    @GetMapping("/bus/{busId}")
    public ApiResponse<List<SeatResponse>> getByBus(@PathVariable Integer busId) {
        return ApiResponse.<List<SeatResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .data(seatService.getSeatsByBusId(busId))
                .build();
    }
}