package com.project.ibtss.repository;

import com.project.ibtss.model.Stations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Stations,Integer> {
}
