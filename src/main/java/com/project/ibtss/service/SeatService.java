package com.project.ibtss.service;

import com.project.ibtss.dto.request.SeatRequest;
import com.project.ibtss.dto.response.SeatForSelectResponse;
import com.project.ibtss.dto.response.SeatResponse;
import com.project.ibtss.model.Seats;

import java.util.List;

public interface SeatService {
    List<SeatResponse> createSeats(List<SeatRequest> requests);
    List<SeatResponse> updateSeatsBySeatCount(Integer busId, int newSeatCount);
    SeatResponse updateSeat(Integer id, SeatRequest request);
    void deleteSeat(Integer id);
    SeatResponse getSeatById(Integer id);
    List<SeatResponse> getSeatsByBusId(Integer busId);
    List<SeatForSelectResponse> getAllSeatsForSelect(String licensePlate);
    List<SeatForSelectResponse> getListSeatByTrip(Integer tripId);
    List<Seats> setStatusListSeat(List<Integer> seatIds);
}