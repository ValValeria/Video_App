package com.example.rozetka_app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class BaseRepository<T> {
    @PersistenceContext
    protected EntityManager entityManager;

    public abstract void save(T object);
    public abstract void deleteById(Long id);
    public abstract List<T> findAll(Sort id);
    public abstract Page<T> findAll(PageRequest of);
}
