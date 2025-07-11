package com.project.ibtss.repository;

import com.project.ibtss.model.Trips;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface TripRepository extends JpaRepository<Trips, Integer> {
    List<Trips> findByBus_IdAndDepartureTimeLessThanAndArrivalTimeGreaterThan(
            Integer busId,
            LocalDateTime arrivalTime,
            LocalDateTime departureTime
    );

    List<Trips> findByRoute_DepartureStation_NameAndRoute_DestinationStation_Name(String from, String to);

    List<Trips> findAllByRouteId(Integer routeId);

    Page<Trips> findAll(Pageable pageable);

    boolean existsByDriver_IdAndDepartureTimeLessThanAndArrivalTimeGreaterThan(
            Integer driverId,
            LocalDateTime arrivalTime,
            LocalDateTime departureTime
    );

    boolean existsByBus_IdAndDepartureTimeLessThanAndArrivalTimeGreaterThan(
            Integer busId,
            LocalDateTime arrivalTime,
            LocalDateTime departureTime
    );

}
