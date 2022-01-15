package com.example.rozetka_app.repositories;

import com.example.rozetka_app.models.Statistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

interface IStatisticsRepository extends JpaRepository<Statistics, Long> {
    @Query("from Statistics s where s.day >= :localDate")
    Page<Statistics> findAllUpToDay(LocalDate localDate, Pageable pageable);
    Optional<Statistics> findByDayBefore(LocalDate localDate);
}

@Repository
public class StatisticsRepository extends BaseRepository<Statistics> {
    @PersistenceContext
    private EntityManager entityManager;

    public Page<Statistics> findAllUpToDay(LocalDate localDate, Pageable pageable) {
        return null;
    }

    public Optional<Statistics> findByDayBefore(LocalDate localDate) {
        return Optional.empty();
    }

    @Override
    public void save(Statistics object) {

    }

    @Override
    public Optional<Statistics> findById(Long entityId) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Statistics> findAll(Sort id) {
        return null;
    }

    @Override
    public List<Statistics> findAll(PageRequest of) {
        return null;
    }
}
