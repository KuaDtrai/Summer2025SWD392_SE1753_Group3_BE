package com.project.ibtss.repository;

import com.project.ibtss.model.Routes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteRepository extends JpaRepository<Routes,Integer> {
    Optional<Routes> findById(Integer id);
}
