package com.project.ibtss.repository;

import com.project.ibtss.model.Buses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Buses, Integer> {
    boolean existsByLicensePlate(String licensePlate); // Check if a bus with the given license plate already exists
    boolean existsByLicensePlateAndIdNot(String licensePlate, Integer id); // Check if a bus with the same license plate exists but not the same ID
}
