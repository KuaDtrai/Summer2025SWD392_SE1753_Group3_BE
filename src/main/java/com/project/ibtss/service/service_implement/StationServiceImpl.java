package com.project.ibtss.service.service_implement;

import com.project.ibtss.dto.request.StationRequest;
import com.project.ibtss.dto.request.UpdateStationRequest;
import com.project.ibtss.dto.request.UpdateStatusStation;
import com.project.ibtss.dto.response.StationResponse;
import com.project.ibtss.repository.RouteStationRepository;
import com.project.ibtss.repository.TripRepository;
import com.project.ibtss.utilities.enums.ErrorCode;
import com.project.ibtss.utilities.enums.StationStatus;
import com.project.ibtss.utilities.enums.TripsStatus;
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
    private final RouteStationRepository routeStationRepository;
    private final TripRepository tripRepository;

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
        return mapper.toStationResponse(stationRepository.save(station));
    }

    @Override
    public StationResponse updateStatusStation(Integer id, UpdateStatusStation statusStation) {
//        if (getAccount().getRole() != Role.ADMIN) {
//            throw new AppException(ErrorCode.RUNTIME_EXCEPTION);
//        }
        Stations station = stationRepository.getById(id);
        if (station == null) {
            throw new AppException(ErrorCode.STATION_NOT_EXISTED);
        }

        if (statusStation.getStatus().equals(StationStatus.INACTIVE)) {
            List<Integer> routeIds = routeStationRepository
                    .findAllByStation_Id(station.getId())
                    .stream()
                    .map(rs -> rs.getRoute().getId())
                    .distinct()
                    .toList();

            if (!routeIds.isEmpty()) {
                List<TripsStatus> activeStatuses = List.of(
                        TripsStatus.SCHEDULED,
                        TripsStatus.IN_PROGRESS,
                        TripsStatus.DELAYED
                );

                boolean hasActiveTrips = tripRepository.existsByRoute_IdInAndStatusIn(routeIds, activeStatuses);

                if (hasActiveTrips) {
                    throw new AppException(ErrorCode.STATION_USED_IN_ACTIVE_TRIP);
                }
            }
            station.setStatus(StationStatus.INACTIVE);

        } else {
            station.setStatus(StationStatus.ACTIVE);
        }

        try {
            station = stationRepository.save(station);
            return mapper.toStationResponse(station);
        } catch (Exception e) {
            throw new AppException(ErrorCode.RUNTIME_EXCEPTION);
        }
    }


    @Override
    public List<Stations> searchStations(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Stations> resultPage = stationRepository.searchByName(search, pageable);
        return resultPage.getContent();
    }

    @Override
    public Page<StationResponse> getStationsByStatus(Pageable pageable) {
        Page<Stations> stationsPage = stationRepository.findAllByStatus(
                StationStatus.ACTIVE, pageable
        );

        return stationsPage.map(mapper::toStationResponse);
    }
}
