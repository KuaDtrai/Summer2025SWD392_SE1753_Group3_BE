package com.project.ibtss.controller;

import com.project.ibtss.dto.request.SearchTripRequest;
import com.project.ibtss.dto.request.TripRequest;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.TripResponse;
import com.project.ibtss.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse<List<TripResponse>> getAllTrips() {
        return ApiResponse.<List<TripResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .data(tripService.getAllTrips())
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
    @PreAuthorize("hasAuthority('admin:create')")
    public ApiResponse<TripResponse> createTrip(@Valid @RequestBody TripRequest request) {
        return ApiResponse.<TripResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Trip created successfully")
                .data(tripService.createTrip(request))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ApiResponse<TripResponse> updateTrip(@PathVariable Integer id, @Valid @RequestBody TripRequest request) {
        return ApiResponse.<TripResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Trip updated successfully")
                .data(tripService.updateTrip(id, request))
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
}
