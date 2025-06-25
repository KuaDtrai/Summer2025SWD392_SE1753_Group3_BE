package com.project.ibtss.repository;

import com.project.ibtss.model.Trips;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface TripRepository extends JpaRepository<Trips, Integer> {
    @Query("""
        SELECT t FROM Trips t
        WHERE t.status = 'ACTIVE'
        AND ((t.bus.id = :busId OR t.driver.id = :driverId)
        AND (:start < t.arrivalTime AND :end > t.departureTime))
    """)
    List<Trips> findConflictingTrips(LocalDateTime start, LocalDateTime end, Integer busId, Integer driverId);

    List<Trips> findByRoute_DepartureStation_NameAndRoute_DestinationStation_Name(String from, String to);

    List<Trips> findAllByRouteId(Integer routeId);

            //    Trips findByTicketSegmentId(Integer ticketSegmentId);
}
