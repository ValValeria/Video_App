package com.example.rozetka_app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.example.rozetka_app.models.Statistics;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class BaseRepository<T> {
    @PersistenceContext
    protected EntityManager entityManager;

    public abstract void save(T object);
    public abstract Optional<T> findById(Long entityId);
    public abstract void deleteById(Long id);
    public abstract List<Statistics> findAll(Sort id);
    public abstract List<T> findAll(PageRequest of);
}
