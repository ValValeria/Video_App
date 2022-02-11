package com.example.rozetka_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.rozetka_app.models.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
   @Query("from Comment c where c.video.id = :videoId")
   List<Comment> findAllByVideoId(Long videoId);
}
