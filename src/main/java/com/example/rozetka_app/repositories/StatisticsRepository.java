package com.example.rozetka_app.repositories;

import com.example.rozetka_app.models.Statistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    @Query("from Statistics s where s.day >= :localDate")
    Page<Statistics> findAllUpToDay(LocalDate localDate, Pageable pageable);
}
