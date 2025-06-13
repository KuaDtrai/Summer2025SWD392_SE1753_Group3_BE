package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.TripRequest;
import com.project.ibtss.dto.response.TripResponse;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.mapper.TripMapper;
import com.project.ibtss.model.*;
import com.project.ibtss.repository.*;
import com.project.ibtss.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final TripMapper tripMapper;
    private final RouteRepository routeRepository;
    private final BusRepository busRepository;
    private final AccountRepository accountRepository;

    @Override
    public TripResponse createTrip(TripRequest request) {
        Trips trip = tripMapper.toEntity(request);
        trip.setRoute(routeRepository.findById(request.getRouteId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
        trip.setBus(busRepository.findById(request.getBusId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
        trip.setDriver(accountRepository.findById(request.getDriverId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
        return tripMapper.toResponse(tripRepository.save(trip));
    }

    @Override
    public TripResponse updateTrip(Integer id, TripRequest request) {
        Trips trip = tripRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        tripMapper.updateTripFromRequest(request, trip);
        trip.setRoute(routeRepository.findById(request.getRouteId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
        trip.setBus(busRepository.findById(request.getBusId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
        trip.setDriver(accountRepository.findById(request.getDriverId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
        return tripMapper.toResponse(tripRepository.save(trip));
    }

    @Override
    public TripResponse getTripById(Integer id) {
        return tripRepository.findById(id)
                .map(tripMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public void deleteTrip(Integer id) {
        if (!tripRepository.existsById(id)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        tripRepository.deleteById(id);
    }

    @Override
    public List<TripResponse> getAllTrips() {
        return tripRepository.findAll().stream()
                .map(tripMapper::toResponse)
                .collect(Collectors.toList());
    }
}