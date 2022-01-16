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

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    @Query("from Statistics s where s.day >= :localDate")
    Page<Statistics> findAllUpToDay(LocalDate localDate, Pageable pageable);
    List<Statistics> findByDayBefore(LocalDate localDate, Sort sort);
}
