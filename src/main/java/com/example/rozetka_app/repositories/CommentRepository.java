package com.example.rozetka_app.repositories;

import com.example.rozetka_app.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
