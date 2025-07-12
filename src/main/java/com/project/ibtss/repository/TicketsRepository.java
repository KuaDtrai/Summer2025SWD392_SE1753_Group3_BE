package com.project.ibtss.repository;

import com.project.ibtss.model.Tickets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketsRepository extends JpaRepository<Tickets, Integer> {
    Page<Tickets> findAllByAccountId(int accountId, Pageable pageable);
    Optional<Tickets> findByJourneyCode(String journeyCode);
}
