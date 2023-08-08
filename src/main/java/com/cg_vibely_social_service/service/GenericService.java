package com.cg_vibely_social_service.service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public interface GenericService<T> {
    List<T> findAll();

    Optional<T> findById(Long id);

    T save(T t);

    T update(Long id, T t) throws EntityNotFoundException;

    void remove(Long id) throws EntityNotFoundException;
}
