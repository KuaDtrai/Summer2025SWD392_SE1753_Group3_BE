package com.project.ibtss.repository;

import com.project.ibtss.model.Buses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusRepository extends JpaRepository<Buses, Integer> {
    boolean existsByLicensePlateIgnoreCase(String licensePlate);
    List<Buses> findByLicensePlateContainingIgnoreCase(String licensePlate);
}