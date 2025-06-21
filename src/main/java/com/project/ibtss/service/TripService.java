package com.project.ibtss.service;

import com.project.ibtss.dto.request.SearchTripRequest;
import com.project.ibtss.dto.request.TripRequest;
import com.project.ibtss.dto.response.TripResponse;

import java.util.List;

public interface TripService {
    TripResponse createTrip(TripRequest request);
    TripResponse updateTrip(Integer id, TripRequest request);
    TripResponse getTripById(Integer id);
    void deleteTrip(Integer id);
    List<TripResponse> getAllTrips();
    List<TripResponse> searchTrip(SearchTripRequest request);
}