package com.example.rozetka_app.repositories;

import com.example.rozetka_app.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
    AppUser findUserById(Long id);
}
