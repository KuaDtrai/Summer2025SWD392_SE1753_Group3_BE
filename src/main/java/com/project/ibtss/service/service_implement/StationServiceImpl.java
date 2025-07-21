package com.project.ibtss.service.service_implement;

import com.project.ibtss.dto.request.StationRequest;
import com.project.ibtss.dto.request.UpdateStationRequest;
import com.project.ibtss.dto.response.StationResponse;
import com.project.ibtss.utilities.enums.ErrorCode;
import com.project.ibtss.utilities.enums.StationStatus;
import com.project.ibtss.utilities.exception.AppException;
import com.project.ibtss.utilities.mapper.StationMapper;
import com.project.ibtss.model.Account;
import com.project.ibtss.model.Stations;
import com.project.ibtss.repository.AccountRepository;
import com.project.ibtss.repository.StationRepository;
import com.project.ibtss.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StationServiceImpl implements StationService {
    private final AccountRepository accountRepository;
    private final StationRepository stationRepository;
    private final StationMapper mapper;

    private Account getAccount() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountRepository.findByPhone(account.getPhone());
    }

    @Override
    public List<StationResponse> getActiveStation() {
        List<StationResponse> stationResponseList = new ArrayList<>();
        List<Stations> stations = stationRepository.getStationsByStatus(StationStatus.ACTIVE.getName());
        for (Stations station : stations) {
            stationResponseList.add(mapper.toStationResponse(station));
        }
        return stationResponseList;
    }

    @Override
    public StationResponse getStationById(Integer id) {
        return mapper.toStationResponse(stationRepository.getById(id));
    }

    @Override
    public Page<StationResponse> getAllStation(Pageable pageable) {
        Page<Stations> stationPage = stationRepository.findAll(pageable);
        return stationPage.map(mapper::toStationResponse);
    }

    @Override
    public StationResponse createStation(StationRequest stationRequest) {
//        if (getAccount().getRole() != Role.ADMIN) {
//            throw new AppException(ErrorCode.RUNTIME_EXCEPTION);
//        }
        Stations station = new Stations();
        station.setName(stationRequest.getName());
        station.setAddress(stationRequest.getAddress());
        station.setStatus(StationStatus.ACTIVE);
        station.setCreatedDate(LocalDateTime.now());
        return mapper.toStationResponse(stationRepository.save(station));
    }

    @Override
    public StationResponse updateStation(Integer id, UpdateStationRequest stationRequest) {
//        if (getAccount().getRole() != Role.ADMIN) {
//            throw new AppException(ErrorCode.RUNTIME_EXCEPTION);
//        }
        Stations station = stationRepository.getById(id);
        station.setName(stationRequest.getName());
        station.setAddress(stationRequest.getAddress());
        station.setStatus(StationStatus.valueOf(stationRequest.getStatus().toUpperCase()));
        return mapper.toStationResponse(stationRepository.save(station));
    }

    @Override
    public StationResponse deleteStationById(Integer id) {
//        if (getAccount().getRole() != Role.ADMIN) {
//            throw new AppException(ErrorCode.RUNTIME_EXCEPTION);
//        }
        Stations station = stationRepository.getById(id);
        if(station == null){
            throw new AppException(ErrorCode.STATION_NOT_EXISTED);
        }
        try {
            station.setStatus(StationStatus.INACTIVE);
            return mapper.toStationResponse(stationRepository.save(station));
        }catch (Exception e){
            throw new AppException(ErrorCode.RUNTIME_EXCEPTION);
        }
    }

    @Override
    public List<Stations> searchStations(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Stations> resultPage = stationRepository.searchByName(search, pageable);
        return resultPage.getContent();
    }
}
