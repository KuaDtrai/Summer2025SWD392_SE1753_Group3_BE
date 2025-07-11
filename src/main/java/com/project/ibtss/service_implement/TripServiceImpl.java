package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.*;
import com.project.ibtss.dto.response.TripResponse;
import com.project.ibtss.enums.*;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.mapper.TripMapper;
import com.project.ibtss.model.*;
import com.project.ibtss.repository.*;
import com.project.ibtss.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private final StaffRepository staffRepository;

    private final int BUS_BREAK_TIME = 60;
    private final int DRIVER_BREAK_TIME = 120;
    private final int MINUTE_BETWEEN_TRIP = 60;
    private final int HOUR_BETWEEN_TRIP = 1;

    @Override
    public TripResponse createTrip(TripCreateRequest request) {

        Routes routes = routeRepository.findById(request.getRouteId()).orElseThrow(() -> new AppException(ErrorCode.ROUTE_NOT_EXISTED));
        Account driver = accountRepository.findById(request.getAccountId()).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        if(!driver.getRole().equals(Role.STAFF)){
            throw new AppException(ErrorCode.NOT_STAFF_ROLE);
        }
        Staff staff = staffRepository.findByAccountId(driver.getId()).orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));
        if(!staff.getPosition().equals(Position.DRIVER)){
            throw new AppException(ErrorCode.NOT_DRIVER_POSITON);
        }

        Buses bus = busRepository.findById(request.getBusId()).orElseThrow(() -> new AppException(ErrorCode.BUS_NOT_EXISTED));
        if(!bus.getStatus().equals(BusStatus.ACTIVE)){
            throw new AppException(ErrorCode.BUS_NOT_ACTIVE);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime departureTime = LocalDateTime.parse(request.getDepartureTime(), formatter);

        LocalTime estimatedTime = routes.getEstimatedTime();
        Duration duration = Duration.ofHours(estimatedTime.getHour())
                .plusMinutes(estimatedTime.getMinute());
        LocalDateTime arrivalTime = departureTime.plus(duration);

        boolean busConflict = tripRepository.existsByBus_IdAndDepartureTimeLessThanAndArrivalTimeGreaterThan(
                bus.getId(), arrivalTime, departureTime);
        if (busConflict) {
            throw new AppException(ErrorCode.BUS_ALREADY_ASSIGNED_TO_TRIP);
        }

        boolean driverConflict = tripRepository.existsByDriver_IdAndDepartureTimeLessThanAndArrivalTimeGreaterThan(
                driver.getId(), arrivalTime, departureTime);
        if (driverConflict) {
            throw new AppException(ErrorCode.DRIVER_ALREADY_ASSIGNED_TO_TRIP);
        }

        Trips trips = Trips.builder()
                .route(routes)
                .bus(bus)
                .driver(driver)
                .departureTime(departureTime)
                .arrivalTime(arrivalTime)
                .price(request.getPrice())
                .createdAt(LocalDateTime.now())
                .status(TripsStatus.SCHEDULED)
                .build();

        tripRepository.save(trips);

        return tripMapper.toResponse(trips);
    }

    @Override
    public Page<TripResponse> getAllTrips(int page) {
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        return tripRepository.findAll(pageable).map(tripMapper::toResponse);
    }

    @Override
    public List<TripResponse> searchTrip(SearchTripRequest request) {
        List<Trips> trips = tripRepository
                .findByRoute_DepartureStation_NameAndRoute_DestinationStation_Name(request.getFrom(), request.getTo());

        return trips.stream()
                .filter(trip -> trip.getDepartureTime().toLocalDate().equals(request.getDepartureTime()))
                .filter(trip -> trip.getStatus() == TripsStatus.SCHEDULED)
                .map(tripMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void autoGenerateTrips(TripAutoGenerateRequest request) {
        Routes route = routeRepository.findById(request.getRouteId())
                .orElseThrow(() -> new AppException(ErrorCode.ROUTE_NOT_EXISTED));

        LocalDate startDate = LocalDate.parse(request.getStartDate());
        LocalDate endDate = LocalDate.parse(request.getEndDate());

        Duration estimatedTime = Duration.ofHours(route.getEstimatedTime().getHour())
                .plusMinutes(route.getEstimatedTime().getMinute());

        List<Account> allDrivers = accountRepository.findByRoleAndStaff_Position(Role.STAFF, Position.DRIVER);
        List<Buses> allBuses = busRepository.findByStatus(BusStatus.ACTIVE);

        List<Trips> createdTrips = new ArrayList<>();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            LocalTime currentTime = LocalTime.of(request.getStartHour(), 0);
            LocalTime endTime = LocalTime.of(request.getEndHour(), 0);

            if (date.equals(LocalDate.now())) {
                LocalTime now = LocalTime.now().plusHours(HOUR_BETWEEN_TRIP);
                if (now.isAfter(currentTime)) {
                    currentTime = now;
                }
            }

            while (!currentTime.isAfter(endTime)) {
                LocalDateTime departureTime = LocalDateTime.of(date, currentTime);
                LocalDateTime arrivalTime = departureTime.plus(estimatedTime);

                Buses availableBus = findAvailableBus(allBuses, createdTrips, departureTime);
                Account availableDriver = findAvailableDriver(allDrivers, createdTrips, departureTime);

                if (availableBus == null || availableDriver == null) {
                    currentTime = currentTime.plusMinutes(MINUTE_BETWEEN_TRIP);
                    continue;
                }

                Trips trip = Trips.builder()
                        .route(route)
                        .departureTime(departureTime)
                        .arrivalTime(arrivalTime)
                        .price(request.getPrice())
                        .bus(availableBus)
                        .driver(availableDriver)
                        .status(TripsStatus.SCHEDULED)
                        .build();

                createdTrips.add(trip);
                currentTime = currentTime.plusMinutes(MINUTE_BETWEEN_TRIP);
            }
        }

        tripRepository.saveAll(createdTrips);
    }

    private Buses findAvailableBus(List<Buses> buses, List<Trips> existingTrips, LocalDateTime departureTime) {
        for (Buses bus : buses) {
            boolean isAvailable = existingTrips.stream()
                    .filter(trip -> trip.getBus().getId().equals(bus.getId()))
                    .noneMatch(trip -> trip.getArrivalTime().plusMinutes(BUS_BREAK_TIME).isAfter(departureTime));
            if (isAvailable) return bus;
        }
        return null;
    }

    private Account findAvailableDriver(List<Account> drivers, List<Trips> existingTrips, LocalDateTime departureTime) {
        for (Account driver : drivers) {
            boolean isAvailable = existingTrips.stream()
                    .filter(trip -> trip.getDriver().getId().equals(driver.getId()))
                    .noneMatch(trip -> trip.getArrivalTime().plusMinutes(DRIVER_BREAK_TIME).isAfter(departureTime));
            if (isAvailable) return driver;
        }
        return null;
    }

    @Override
    public TripResponse getTripById(Integer id) {
        return tripRepository.findById(id)
                .map(tripMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));
    }

    @Override
    public TripResponse updateTrip(Integer id, TripCreateRequest request) {
        Trips trip = tripRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TRIP_NOT_EXISTED));

        if (!trip.getStatus().equals(TripsStatus.SCHEDULED)) {
            throw new AppException(ErrorCode.CANNOT_EDIT_TRIP);
        }

        Routes route = routeRepository.findById(request.getRouteId())
                .orElseThrow(() -> new AppException(ErrorCode.ROUTE_NOT_EXISTED));

        Account driver = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        if (!driver.getRole().equals(Role.STAFF)) {
            throw new AppException(ErrorCode.NOT_STAFF_ROLE);
        }

        Staff staff = staffRepository.findByAccountId(driver.getId())
                .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));

        if (!staff.getPosition().equals(Position.DRIVER)) {
            throw new AppException(ErrorCode.NOT_DRIVER_POSITON);
        }

        Buses bus = busRepository.findById(request.getBusId())
                .orElseThrow(() -> new AppException(ErrorCode.BUS_NOT_EXISTED));

        if (!bus.getStatus().equals(BusStatus.ACTIVE)) {
            throw new AppException(ErrorCode.BUS_NOT_ACTIVE);
        }

        LocalDateTime departureTime = LocalDateTime.parse(request.getDepartureTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Duration estimatedTime = Duration.ofHours(route.getEstimatedTime().getHour())
                .plusMinutes(route.getEstimatedTime().getMinute());
        LocalDateTime arrivalTime = departureTime.plus(estimatedTime);

        List<Trips> conflictingTrips = tripRepository.findByBus_IdAndDepartureTimeLessThanAndArrivalTimeGreaterThan(
                bus.getId(), departureTime, arrivalTime);

        conflictingTrips = conflictingTrips.stream().filter(t -> !t.getId().equals(trip.getId())).toList();

        if (!conflictingTrips.isEmpty()) {
            throw new AppException(ErrorCode.BUS_ALREADY_ASSIGNED_TO_TRIP);
        }

        trip.setRoute(route);
        trip.setDepartureTime(departureTime);
        trip.setArrivalTime(arrivalTime);
        trip.setBus(bus);
        trip.setDriver(driver);
        trip.setPrice(request.getPrice());

        tripRepository.save(trip);

        return tripMapper.toResponse(trip);
    }

    @Override
    public TripResponse updateTripStatus(Integer id, TripUpdateStatusRequest request) {
        Trips trip = tripRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TRIP_NOT_EXISTED));

        trip.setStatus(request.getStatus());
        tripRepository.save(trip);

        return tripMapper.toResponse(trip);
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
