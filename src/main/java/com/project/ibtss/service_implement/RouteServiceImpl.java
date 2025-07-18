package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.CreateRouteRequest;
import com.project.ibtss.dto.request.RouteUpdateRequest;
import com.project.ibtss.dto.response.RouteResponse;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.enums.RouteStatus;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.mapper.RouteMapper;
import com.project.ibtss.model.RouteStations;
import com.project.ibtss.model.Routes;
import com.project.ibtss.model.Stations;
import com.project.ibtss.repository.RouteRepository;
import com.project.ibtss.repository.RouteStationRepository;
import com.project.ibtss.repository.StationRepository;
import com.project.ibtss.repository.TripRepository;
import com.project.ibtss.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;
    private final StationRepository stationRepository;
    private final RouteMapper routeMapper;
    private final TripRepository tripRepository;
    private final RouteStationRepository routeStationRepository;

    @Override
    public RouteResponse getRouteById(Integer id) {
        Optional<Routes> route = routeRepository.findById(id);
        return routeMapper.toRouteResponse(route.orElse(null));
    }

    @Override
    public Page<RouteResponse> getAllRoute(Pageable pageable) {
        Page<Routes> routes = routeRepository.findAll(pageable);
        return routes.map(routeMapper::toRouteResponse);
    }

    @Override
    public RouteResponse createRoute(CreateRouteRequest createRouteRequest) {
        Stations departureStation = stationRepository.findById(createRouteRequest.getDepartureStationId()).orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_EXISTED));
        Stations destinationStation = stationRepository.findById(createRouteRequest.getDestinationStationId()).orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_EXISTED));

        Routes route = Routes.builder()
                .name(createRouteRequest.getName())
                .departureStation(departureStation)
                .destinationStation(destinationStation)
                .distanceKm(createRouteRequest.getDistanceKm())
                .estimatedTime(createRouteRequest.getEstimatedTime())
                .status(RouteStatus.ACTIVE)
                .build();
        route = routeRepository.save(route);

        RouteStations routeStationsDeparture = RouteStations.builder()
                .route(route)
                .station(departureStation)
                .stationOrder(1)
                .build();
        routeStationRepository.save(routeStationsDeparture);

        for (int i = 0; i < createRouteRequest.getListStationId().size(); i++) {
            Integer stationId = createRouteRequest.getListStationId().get(i);
            Stations station = stationRepository.findById(stationId)
                    .orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_EXISTED));

            RouteStations routeStations = RouteStations.builder()
                    .route(route)
                    .station(station)
                    .stationOrder(i + 2)
                    .build();

            routeStationRepository.save(routeStations);
        }

        int destinationOrder = createRouteRequest.getListStationId().size() + 2;

        RouteStations routeStationsDestination = RouteStations.builder()
                .route(route)
                .station(destinationStation)
                .stationOrder(destinationOrder)
                .build();
        routeStationRepository.save(routeStationsDestination);

        return routeMapper.toRouteResponse(route);
    }

    @Override
    public RouteResponse updateRoute(Integer id, RouteUpdateRequest routeRequest) {
        Stations departureStation = stationRepository.getById(routeRequest.getDepartureStationId());
        Stations destinationStation = stationRepository.getById(routeRequest.getDestinationStationId());
        Routes route = routeRepository.getById(id);
        route.setName(routeRequest.getName());
        route.setEstimatedTime(routeRequest.getEstimatedTime());
        route.setDistanceKm(routeRequest.getDistanceKm());
        route.setDepartureStation(departureStation);
        route.setDestinationStation(destinationStation);
        route.setStatus(RouteStatus.valueOf(routeRequest.getStatus().toUpperCase()));
        return routeMapper.toRouteResponse(routeRepository.save(route));
    }

    @Override
    public RouteResponse deleteRoute(Integer id) {
        Routes route = routeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.RUNTIME_EXCEPTION));
        route.setStatus(RouteStatus.INACTIVE);
        return routeMapper.toRouteResponse(routeRepository.save(route));
    }

    @Override
    public Page<RouteResponse> getAllRouteActive(int page, String search) {
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        return routeRepository.findByStatusAndNameContainingIgnoreCase(RouteStatus.ACTIVE, search, pageable)
                .map(routeMapper::toRouteResponse);
    }

}
