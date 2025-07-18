package com.project.ibtss.service;

import com.project.ibtss.dto.request.StationRequest;
import com.project.ibtss.dto.request.UpdateStationRequest;
import com.project.ibtss.dto.response.StationResponse;
import com.project.ibtss.model.Stations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface StationService {
    List<StationResponse> getActiveStation();
    StationResponse getStationById(Integer id);
    Page<StationResponse> getAllStation(Pageable pageable);
    StationResponse createStation(StationRequest stationRequest);
    StationResponse updateStation(Integer id, UpdateStationRequest stationRequest);
    StationResponse deleteStationById(Integer id);
    List<Stations> searchStations(String search, int page, int size);
}
