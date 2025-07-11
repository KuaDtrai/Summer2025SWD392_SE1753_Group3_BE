package com.project.ibtss.service_implement;

import com.project.ibtss.dto.response.RouteStationSelectResponse;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.model.RouteStations;
import com.project.ibtss.model.Routes;
import com.project.ibtss.model.Trips;
import com.project.ibtss.repository.RouteRepository;
import com.project.ibtss.repository.RouteStationRepository;
import com.project.ibtss.repository.TripRepository;
import com.project.ibtss.service.RouteStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteStationServiceImpl implements RouteStationService {
    private final TripRepository tripRepository;
    private final RouteStationRepository routeRepository;
    @Override
    public List<RouteStationSelectResponse> getAllRouteStationSelectByTrip(int tripId) {
        Trips trip = tripRepository.findById(tripId).orElseThrow(() -> new AppException(ErrorCode.TRIP_NOT_EXISTED));
        Routes route = trip.getRoute();
        List<RouteStations> routeStations = routeRepository.findAllRouteStationByRouteId(route.getId());
        return routeStations.stream()
                .map(this::toRouteStationSelectResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RouteStationSelectResponse> getAllRouteStationSelectByRoute(int routeId) {
        List<RouteStations> routeStations = routeRepository.findAllRouteStationByRouteId(routeId);
        return routeStations.stream()
                .map(this::toRouteStationSelectResponse)
                .collect(Collectors.toList());
    }

    private RouteStationSelectResponse toRouteStationSelectResponse(RouteStations routeStations) {
        return RouteStationSelectResponse.builder()
                .stationId(routeStations.getId())
                .stationName(routeStations.getStation().getName())
                .address(routeStations.getStation().getAddress())
                .stationOrder(routeStations.getStationOrder())
                .build();
    }
}
