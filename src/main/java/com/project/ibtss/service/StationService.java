package com.project.ibtss.service;

import com.project.ibtss.dto.request.StationRequest;
import com.project.ibtss.dto.response.StationResponse;
import com.project.ibtss.model.Stations;

import java.util.List;

public interface StationService
//        extends BaseService<Stations>
{
    StationResponse getStationById(Integer id);
    List<StationResponse> getAllStation();
    StationResponse createStation(StationRequest stationRequest);
    StationResponse updateStation(Integer id,StationRequest stationRequest);
    StationResponse deleteStationById(Integer id);
}
