package com.project.ibtss.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BaseService<T> {
    Optional<T> getById(UUID id);
    List<T> getAll();
    T create(T entity);
    T update(T entity);
    boolean delete(UUID id);
    boolean existsById(UUID id);
}
