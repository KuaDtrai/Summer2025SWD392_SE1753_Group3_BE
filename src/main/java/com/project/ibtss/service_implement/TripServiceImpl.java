package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.SearchTripRequest;
import com.project.ibtss.dto.request.TripRequest;
import com.project.ibtss.dto.response.TripResponse;
import com.project.ibtss.enums.BusStatus;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.enums.TripsStatus;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.mapper.TripMapper;
import com.project.ibtss.model.*;
import com.project.ibtss.repository.*;
import com.project.ibtss.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final RouteRepository routeRepository;
    private final BusRepository busRepository;
    private final AccountRepository accountRepository;
//    private final TicketRepository ticketRepository;
    private final TripMapper tripMapper;

    private boolean isOverlappingTrip(LocalDateTime start, LocalDateTime end, Integer busId, Integer driverId) {
        return !tripRepository.findConflictingTrips(start, end, busId, driverId).isEmpty();
    }

    @Override
    public TripResponse createTrip(TripRequest request) {
        if (!request.getDepartureTime().isBefore(request.getArrivalTime())) {
            throw new AppException(ErrorCode.INVALID_TIME_RANGE);
        }

        if (isOverlappingTrip(request.getDepartureTime(), request.getArrivalTime(), request.getBusId(), request.getDriverId())) {
            throw new AppException(ErrorCode.CONFLICTING_TRIP);
        }

        Buses bus = busRepository.findById(request.getBusId())
                .filter(b -> BusStatus.ACTIVE.equals(b.getStatus()))
                .orElseThrow(() -> new AppException(ErrorCode.BUS_INACTIVE));

        Account driver = accountRepository.findById(request.getDriverId())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_DRIVER));

        Trips trip = tripMapper.toEntity(request);
        trip.setRoute(routeRepository.findById(request.getRouteId())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY)));
        trip.setBus(bus);
        trip.setDriver(driver);
        trip.setStatus(TripsStatus.WAITING);

        return tripMapper.toResponse(tripRepository.save(trip));
    }

    @Override
    public List<TripResponse> getAllTrips() {
        return tripRepository.findAll().stream()
                .map(tripMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TripResponse> searchTrip(SearchTripRequest request) {
        List<Trips> trips = tripRepository
                .findByRoute_DepartureStation_NameAndRoute_DestinationStation_Name(request.getFrom(), request.getTo());

        return trips.stream()
                .filter(trip -> trip.getDepartureTime().toLocalDate().equals(request.getDepartureTime()))
                .filter(trip -> trip.getStatus() == TripsStatus.WAITING)
                .map(tripMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TripResponse getTripById(Integer id) {
        return tripRepository.findById(id)
                .map(tripMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));
    }

    @Override
    public TripResponse updateTrip(Integer id, TripRequest request) {
        Trips trip = tripRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        if (trip.getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.CANNOT_MODIFY_PAST_TRIP);
        }

        if (!request.getDepartureTime().isBefore(request.getArrivalTime())) {
            throw new AppException(ErrorCode.INVALID_TIME_RANGE);
        }

        if (isOverlappingTrip(request.getDepartureTime(), request.getArrivalTime(), request.getBusId(), request.getDriverId())) {
            throw new AppException(ErrorCode.CONFLICTING_TRIP);
        }

        tripMapper.updateFromRequest(request, trip);
        trip.setRoute(routeRepository.findById(request.getRouteId())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY)));
        trip.setBus(busRepository.findById(request.getBusId())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY)));
        trip.setDriver(accountRepository.findById(request.getDriverId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));

        return tripMapper.toResponse(tripRepository.save(trip));
    }

//    @Override
//    public void deleteTrip(Integer id) {
//        Trips trip = tripRepository.findById(id)
//                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));
//
////        if (ticketRepository.existsByTripId(id)) {
////            throw new AppException(ErrorCode.CANNOT_DELETE_TRIP_WITH_TICKETS);
////        }
//
//        tripRepository.save(trip);
//    }
}
