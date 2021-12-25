package com.example.rozetka_app.repositories;

import com.example.rozetka_app.models.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    Page<Video> findAllByTitleLikeOrAuthorLike(String title, String author, PageRequest pageable);
    Video findVideoById(Long id);
    void deleteVideoById(Long id);
}
