package com.project.ibtss.service_implement;

import com.project.ibtss.service.RouteService;
import org.springframework.util.RouteMatcher;

import java.util.List;
import java.util.Optional;

public class RouteServiceImpl implements RouteService {
    @Override
    public Optional<RouteMatcher.Route> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<RouteMatcher.Route> getAll() {
        return List.of();
    }

    @Override
    public RouteMatcher.Route create(RouteMatcher.Route entity) {
        return null;
    }

    @Override
    public RouteMatcher.Route update(RouteMatcher.Route entity) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }
}
