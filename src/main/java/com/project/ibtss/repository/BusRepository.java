package com.project.ibtss.repository;

import com.project.ibtss.model.Buses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusRepository extends JpaRepository<Buses, Integer> {
    boolean existsByLicensePlateIgnoreCase(String licensePlate);
    List<Buses> findByLicensePlateContainingIgnoreCase(String licensePlate);
    Optional<Buses> findByLicensePlateIgnoreCase(String licensePlate);
}