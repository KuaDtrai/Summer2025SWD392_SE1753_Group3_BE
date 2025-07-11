package com.project.ibtss.service;

import com.project.ibtss.dto.request.*;
import com.project.ibtss.dto.response.TripResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TripService {
    TripResponse createTrip(TripCreateRequest request);
    TripResponse updateTrip(Integer id, TripCreateRequest request);
    TripResponse updateTripStatus(Integer id, TripUpdateStatusRequest request);

    TripResponse getTripById(Integer id);
//    void deleteTrip(Integer id);
Page<TripResponse> getAllTrips(int page);
    List<TripResponse> searchTrip(SearchTripRequest request);
    void autoGenerateTrips(TripAutoGenerateRequest request);
}