package com.project.ibtss.repository;

import com.project.ibtss.model.RouteStations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteStationRepository extends JpaRepository<RouteStations, Integer> {
    Optional<RouteStations> findByStationId(int stationId);
    List<RouteStations> findAllRouteStationByRouteId(int routeId);
}
