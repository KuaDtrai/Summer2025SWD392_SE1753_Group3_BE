package com.project.ibtss.service_implement;

import com.project.ibtss.model.Stations;
import com.project.ibtss.service.StationService;

import java.util.List;
import java.util.Optional;

public class StationServiceImpl implements StationService {
    @Override
    public Optional<Stations> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Stations> getAll() {
        return List.of();
    }

    @Override
    public Stations create(Stations entity) {
        return null;
    }

    @Override
    public Stations update(Stations entity) {
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
