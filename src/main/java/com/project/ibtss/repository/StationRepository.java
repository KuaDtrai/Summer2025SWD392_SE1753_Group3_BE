package com.project.ibtss.repository;

import com.project.ibtss.model.Stations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StationRepository extends JpaRepository<Stations,Integer> {
    List<Stations> getStationsByStatus(String status);
    List<Stations> findAll();
    Stations getById(Integer id);

    @Query("SELECT s FROM Stations s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Stations> searchByName(@Param("search") String search, Pageable pageable);
}
