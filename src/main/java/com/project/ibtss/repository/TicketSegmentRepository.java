package com.project.ibtss.repository;

import com.project.ibtss.utilities.enums.TicketStatus;
import com.project.ibtss.model.TicketSegment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketSegmentRepository extends JpaRepository<TicketSegment, Integer> {
    Optional<TicketSegment> findByTicketId(Integer ticketId);
    Optional<TicketSegment> findBySeatId(Integer seatId);
    List<TicketSegment> findAllByTicketId(Integer ticketId);
    int countBySeat_Bus_IdAndTicket_StatusIn(Integer busId, List<String> statuses);
    boolean existsByTrip_Route_IdAndTicket_StatusIn(Integer routeId, List<TicketStatus> statuses);
    boolean existsByTripIdAndTicket_Status(Integer tripId, TicketStatus status);
    List<TicketSegment> findAllByTripIdAndTicket_Status(Integer tripId, TicketStatus status);

}
