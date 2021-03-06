package com.example.rozetka_app.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.rozetka_app.models.Video;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    @Query("from Video v where v.user.username like :search or v.description like :search or v.title like :search")
    Page<Video> findAllByText(String search, Pageable pageable);

    @Query("from Video v where v.user.id = :id")
    Page<Video> findByUserId(Long id, Pageable pageable);

    Video findVideoById(Long id);

    void deleteVideoById(Long id);

    Optional<Video> findById(Long videoId);
}