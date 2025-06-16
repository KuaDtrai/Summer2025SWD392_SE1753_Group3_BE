package com.project.ibtss.service;

import com.project.ibtss.dto.request.SeatRequest;
import com.project.ibtss.dto.response.SeatResponse;

import java.util.List;

public interface SeatService {
    List<SeatResponse> createSeats(List<SeatRequest> requests);
    List<SeatResponse> autoGenerateSeats(Integer busId);
    SeatResponse updateSeat(Integer id, SeatRequest request);
    void deleteSeat(Integer id);
    SeatResponse getSeatById(Integer id);
    List<SeatResponse> getSeatsByBusId(Integer busId);
}