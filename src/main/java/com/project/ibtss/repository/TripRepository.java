package com.project.ibtss.repository;

import com.project.ibtss.model.Trips;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trips, Integer> {
}