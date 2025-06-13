package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.StationRequest;
import com.project.ibtss.dto.response.StationResponse;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.mapper.StationMapper;
import com.project.ibtss.model.Account;
import com.project.ibtss.model.Stations;
import com.project.ibtss.repository.AccountRepository;
import com.project.ibtss.repository.StationRepository;
import com.project.ibtss.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationServiceImpl
//        extends BaseServiceImpl<Stations, StationRepository>
        implements StationService {
    private final AccountRepository accountRepository;
    private final StationRepository stationRepository;
    private final StationMapper mapper;
    
    @Autowired
    public StationServiceImpl(AccountRepository accountRepository, StationRepository stationRepository, StationMapper stationMapper) {
        this.accountRepository = accountRepository;
//        super(stationRepository);
        this.stationRepository = stationRepository;
        this.mapper = stationMapper;
    }

    private Account getAccount() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountRepository.findByPhone(account.getPhone());
    }

    @Override
    public List<StationResponse> getActiveStation() {
        List<StationResponse> stationResponseList = new ArrayList<>();
        List<Stations> stations = stationRepository.getStationsByActiveIsTrue();
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
    public List<StationResponse> getAllStation() {
//        if (getAccount().getRole() != Role.ADMIN) {
//            throw new AppException(ErrorCode.RUNTIME_EXCEPTION);
//        }
        List<StationResponse> stationResponseList = new ArrayList<>();
        System.out.println();
        List<Stations> stations = stationRepository.findAll();
        for (Stations station : stations) {
            StationResponse stationResponse = mapper.toStationResponse(station);
            stationResponseList.add(stationResponse);
        }
        return stationResponseList;
    }

    @Override
    public StationResponse createStation(StationRequest stationRequest) {
//        if (getAccount().getRole() != Role.ADMIN) {
//            throw new AppException(ErrorCode.RUNTIME_EXCEPTION);
//        }
        Stations station = new Stations();
        station.setName(stationRequest.getName());
        station.setAddress(stationRequest.getAddress());
        station.setActive(true);
        return mapper.toStationResponse(stationRepository.save(station));
    }

    @Override
    public StationResponse updateStation(Integer id, StationRequest stationRequest) {
//        if (getAccount().getRole() != Role.ADMIN) {
//            throw new AppException(ErrorCode.RUNTIME_EXCEPTION);
//        }
        Stations station = stationRepository.getById(id);
        station.setName(stationRequest.getName());
        station.setAddress(stationRequest.getAddress());
        return mapper.toStationResponse(stationRepository.save(station));
    }

    @Override
    public StationResponse deleteStationById(Integer id) {
//        if (getAccount().getRole() != Role.ADMIN) {
//            throw new AppException(ErrorCode.RUNTIME_EXCEPTION);
//        }
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
