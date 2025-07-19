package com.project.ibtss.controller;

import com.project.ibtss.dto.request.*;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.TripInfoResponse;
import com.project.ibtss.dto.response.TripResponse;
import com.project.ibtss.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping
    public ApiResponse<Page<TripResponse>> getAllTrips(@RequestParam(defaultValue = "1") int page) {
        return ApiResponse.<Page<TripResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .data(tripService.getAllTrips(page))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<TripResponse> getTripById(@PathVariable Integer id) {
        return ApiResponse.<TripResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .data(tripService.getTripById(id))
                .build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('staff:create')")
    public ApiResponse<TripResponse> createTrip(@Valid @RequestBody TripCreateRequest request) {
        return ApiResponse.<TripResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Trip created successfully")
                .data(tripService.createTrip(request))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('staff:update')")
    public ApiResponse<TripResponse> updateTrip(@PathVariable Integer id, @RequestBody TripCreateRequest request) {
        return ApiResponse.<TripResponse>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(tripService.updateTrip(id, request))
                .build();
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('staff:update')")
    public ApiResponse<TripResponse> updateTripStatus(@PathVariable Integer id, @RequestBody TripUpdateStatusRequest request) {
        return ApiResponse.<TripResponse>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(tripService.updateTripStatus(id, request))
                .build();
    }

//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('admin:delete')")
//    public ApiResponse<Void> deleteTrip(@PathVariable Integer id) {
//        tripService.deleteTrip(id);
//        return ApiResponse.<Void>builder()
//                .code(HttpStatus.OK.value())
//                .message("Trip deleted successfully")
//                .build();
//    }

    @PostMapping("/search")
    public ApiResponse<List<TripResponse>> searchTrips(@RequestBody SearchTripRequest request) {
        return  ApiResponse.<List<TripResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(tripService.searchTrip(request))
                .build();
    }

    @PostMapping("/auto-generate")
    public ApiResponse<String> autoGenerateTrips(@RequestBody TripAutoGenerateRequest request) {
        tripService.autoGenerateTrips(request);
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Tạo chuyến thành công")
                .data("Success")
                .build();
    }

    @GetMapping("/info/{ticketId}")
    public ApiResponse<TripInfoResponse> getTripInfo(@PathVariable Integer ticketId){
        return ApiResponse.<TripInfoResponse>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(tripService.getTripInfo(ticketId))
                .build();
    }
}
