package com.project.ibtss.repository;

import com.project.ibtss.model.TicketSegment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketSegmentRepository extends JpaRepository<TicketSegment, Integer> {
    Optional<TicketSegment> findByTicketId(Integer ticketId);
}
