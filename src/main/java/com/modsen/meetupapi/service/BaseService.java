package com.modsen.meetupapi.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T, ID> {
    List<T> getAllEntities(String sortBy, String sortDir, String filterBy, String filterValue);

    Optional<T> getEntityById(ID id);

    Optional<T> createOrUpdateEntity(T entity);

    void deleteEntity(ID id);
}
