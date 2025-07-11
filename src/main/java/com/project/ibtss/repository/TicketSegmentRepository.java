package com.project.ibtss.repository;

import com.project.ibtss.model.TicketSegment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketSegmentRepository extends JpaRepository<TicketSegment, Integer> {
    Optional<TicketSegment> findByTicketId(Integer ticketId);
    Optional<TicketSegment> findBySeatId(Integer seatId);
    List<TicketSegment> findAllByTicketId(Integer ticketId);
}
