package com.example.rozetka_app.repositories;

import com.example.rozetka_app.models.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
}
