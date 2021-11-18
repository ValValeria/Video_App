package com.example.rozetka_app.repositories;

import com.example.rozetka_app.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findAllByTitleLikeOrAuthorLike(String title, String author);
    void deleteVideoById(Long id);
}
