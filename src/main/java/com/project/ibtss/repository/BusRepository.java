package com.project.ibtss.repository;

import com.project.ibtss.enums.BusStatus;
import com.project.ibtss.model.Buses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusRepository extends JpaRepository<Buses, Integer> {
    boolean existsByLicensePlateIgnoreCase(String licensePlate);
    List<Buses> findByLicensePlateContainingIgnoreCase(String licensePlate);
    Optional<Buses> findByLicensePlateIgnoreCase(String licensePlate);
    Page<Buses> findByLicensePlateContainingIgnoreCase(String licensePlate, Pageable pageable);
    List<Buses> findByStatus(BusStatus status);

}