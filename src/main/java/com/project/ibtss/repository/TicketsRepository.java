package com.project.ibtss.repository;

import com.project.ibtss.model.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketsRepository extends JpaRepository<Tickets, Integer> {
    List<Tickets> findAllByAccountId(int accountId);
    Optional<Tickets> findByJourneyCode(String journeyCode);
}
