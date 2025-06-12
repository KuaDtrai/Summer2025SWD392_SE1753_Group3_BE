package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.StationRequest;
import com.project.ibtss.dto.response.StationResponse;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.mapper.StationMapper;
import com.project.ibtss.model.Stations;
import com.project.ibtss.repository.StationRepository;
import com.project.ibtss.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationServiceImpl
//        extends BaseServiceImpl<Stations, StationRepository>
        implements StationService {
    private final StationRepository stationRepository;
    private final StationMapper mapper;
    
    @Autowired
    public StationServiceImpl(StationRepository stationRepository, StationMapper stationMapper) {
//        super(stationRepository);
        this.stationRepository = stationRepository;
        this.mapper = stationMapper;
    }

    @Override
    public StationResponse getStationById(Integer id) {
        return mapper.toStationResponse(stationRepository.getById(id));
    }

    @Override
    public List<StationResponse> getAllStation() {
        List<StationResponse> stationResponseList = new ArrayList<>();
        List<Stations> stations = stationRepository.findAll();
        for (Stations station : stations) {
            StationResponse stationResponse = mapper.toStationResponse(station);
            stationResponseList.add(stationResponse);
        }
        return stationResponseList;
    }

    @Override
    public StationResponse createStation(StationRequest stationRequest) {
        Stations station = new Stations();
        station.setName(stationRequest.getName());
        station.setAddress(stationRequest.getAddress());
        return mapper.toStationResponse(stationRepository.save(station));
    }

    @Override
    public StationResponse updateStation(Integer id, StationRequest stationRequest) {
        Stations station = stationRepository.getById(id);
        station.setName(stationRequest.getName());
        station.setAddress(stationRequest.getAddress());
        return mapper.toStationResponse(stationRepository.save(station));
    }

    @Override
    public StationResponse deleteStationById(Integer id) {
        Stations station = stationRepository.getById(id);
        if(station == null){
            throw new AppException(ErrorCode.RUNTIME_EXCEPTION);
        }
        try {
            station.setActive(false);
            return mapper.toStationResponse(stationRepository.save(station));
        }catch (Exception e){
            throw new AppException(ErrorCode.RUNTIME_EXCEPTION);
        }
    }
}
