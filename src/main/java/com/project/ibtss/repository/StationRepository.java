package com.project.ibtss.repository;

import com.project.ibtss.model.Stations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StationRepository extends JpaRepository<Stations,Integer> {
    List<Stations> findAll();
    Stations getById(Integer id);
}
