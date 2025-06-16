package com.project.ibtss.service;

import com.project.ibtss.dto.request.BusRequest;
import com.project.ibtss.dto.response.BusResponse;

import java.util.List;

public interface BusService {
    BusResponse createBus(BusRequest request);
    List<BusResponse> getAllBuses();
    BusResponse getBusById(Integer id);
    BusResponse updateBus(Integer id, BusRequest request);
    BusResponse setBusActive(Integer id);
    List<BusResponse> searchByLicensePlate(String keyword);
}