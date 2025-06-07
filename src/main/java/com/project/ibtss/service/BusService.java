package com.project.ibtss.service;

import com.project.ibtss.dto.request.BusRequest;
import com.project.ibtss.dto.response.BusResponse;

import java.util.List;

public interface BusService {
    BusResponse createBus(BusRequest busRequest);
    BusResponse getBusById(Integer id);
    List<BusResponse> getAllBuses();
    BusResponse updateBus(Integer id, BusRequest busRequest);
    void deleteBus(Integer id);
}