package com.project.ibtss.repository;

import com.project.ibtss.model.Seats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seats, Integer> {
    List<Seats> findByBusId(Integer busId);
}