package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.TripRequest;
import com.project.ibtss.dto.response.TripResponse;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.mapper.TripMapper;
import com.project.ibtss.model.Account;
import com.project.ibtss.model.Buses;
import com.project.ibtss.model.Routes;
import com.project.ibtss.model.Trips;
import com.project.ibtss.repository.AccountRepository;
import com.project.ibtss.repository.BusRepository;
import com.project.ibtss.repository.RouteRepository;
import com.project.ibtss.repository.TripRepository;
import com.project.ibtss.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final RouteRepository routeRepository;
    private final BusRepository busRepository;
    private final AccountRepository accountRepository;
    private final TripMapper tripMapper;

    @Override
    public TripResponse createTrip(TripRequest request) {
        Trips trip = tripMapper.toEntity(request);
        trip.setRoute(routeRepository.findById(request.getRouteId())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY)));
        trip.setBus(busRepository.findById(request.getBusId())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY)));
        trip.setDriver(accountRepository.findById(request.getDriverId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
        trip.setStatus("ACTIVE");
        return tripMapper.toResponse(tripRepository.save(trip));
    }

    @Override
    public List<TripResponse> getAllTrips() {
        return tripRepository.findAll().stream()
                .filter(t -> "ACTIVE".equals(t.getStatus()))
                .map(tripMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TripResponse getTripById(Integer id) {
        return tripRepository.findById(id)
                .filter(t -> "ACTIVE".equals(t.getStatus()))
                .map(tripMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));
    }

    @Override
    public TripResponse updateTrip(Integer id, TripRequest request) {
        Trips trip = tripRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));
        tripMapper.updateFromRequest(request, trip);
        trip.setRoute(routeRepository.findById(request.getRouteId())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY)));
        trip.setBus(busRepository.findById(request.getBusId())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY)));
        trip.setDriver(accountRepository.findById(request.getDriverId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
        return tripMapper.toResponse(tripRepository.save(trip));
    }

    @Override
    public void deleteTrip(Integer id) {
        Trips trip = tripRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));
        trip.setStatus("INACTIVE");
        tripRepository.save(trip);
    }
}