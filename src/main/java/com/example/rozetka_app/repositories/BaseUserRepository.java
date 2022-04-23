package com.example.rozetka_app.repositories;

import com.example.rozetka_app.models.AppUser;
import com.example.rozetka_app.models.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseUserRepository extends JpaRepository<BaseUser, Long> {
}
