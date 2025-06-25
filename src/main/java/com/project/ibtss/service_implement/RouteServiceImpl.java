package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.RouteRequest;
import com.project.ibtss.dto.request.RouteUpdateRequest;
import com.project.ibtss.dto.response.RouteResponse;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.enums.RouteStatus;
import com.project.ibtss.enums.TripsStatus;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.mapper.RouteMapper;
import com.project.ibtss.model.Account;
import com.project.ibtss.model.Routes;
import com.project.ibtss.model.Stations;
import com.project.ibtss.model.Trips;
import com.project.ibtss.repository.RouteRepository;
import com.project.ibtss.repository.StationRepository;
import com.project.ibtss.repository.TripRepository;
import com.project.ibtss.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;
    private final StationRepository stationRepository;
    private final RouteMapper routeMapper;
    private final TripRepository tripRepository;

    @Override
    public RouteResponse getRouteById(Integer id) {
        Optional<Routes> route = routeRepository.findById(id);
        return routeMapper.toRouteResponse(route.orElse(null));
    }

    @Override
    public List<RouteResponse> getAllRoute() {
        List<Routes> routes = routeRepository.findAll();
        List<RouteResponse> routeResponseList = new ArrayList<>();
        for (Routes route : routes) {
            routeResponseList.add(routeMapper.toRouteResponse(route));
        }
        return routeResponseList;
    }

    @Override
    public RouteResponse createRoute(RouteRequest routeRequest) {
        Stations departureStation = stationRepository.findById(routeRequest.getDepartureStationId()).orElseThrow(() -> new AppException(ErrorCode.RUNTIME_EXCEPTION));
        Stations destinationStation = stationRepository.findById(routeRequest.getDestinationStationId()).orElseThrow(() -> new AppException(ErrorCode.RUNTIME_EXCEPTION));

        Routes route = new Routes();
        route.setName(routeRequest.getName());
        route.setDepartureStation(departureStation);
        route.setDestinationStation(destinationStation);
        route.setDistanceKm(routeRequest.getDistanceKm());
        route.setEstimatedTime(routeRequest.getEstimatedTime());
        route.setStatus(RouteStatus.ACTIVE);
        return routeMapper.toRouteResponse(routeRepository.save(route));
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

}
