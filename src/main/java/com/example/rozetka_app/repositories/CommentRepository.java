package com.example.rozetka_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rozetka_app.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
