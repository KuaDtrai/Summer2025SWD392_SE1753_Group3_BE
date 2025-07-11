package com.project.ibtss.repository;

import com.project.ibtss.enums.RouteStatus;
import com.project.ibtss.model.Routes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteRepository extends JpaRepository<Routes,Integer> {
    Optional<Routes> findById(Integer id);
    Page<Routes> findByStatusAndNameContainingIgnoreCase(RouteStatus status, String name, Pageable pageable);

}
