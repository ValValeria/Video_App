package com.example.rozetka_app.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.rozetka_app.models.Statistics;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.example.rozetka_app.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepository extends BaseRepository<Comment>{
    @PersistenceContext
    private EntityManager entityManager;

    public Comment findCommentById(Long id) {
        return this.entityManager.find(Comment.class, id);
    }

    @Override
    public void save(Comment object) {

    }

    @Override
    public Optional<Comment> findById(Long entityId) {
        return Optional.empty();
    }

    public void deleteById(Long id) {
    }

    @Override
    public List<Statistics> findAll(Sort id) {
        return null;
    }

    @Override
    public List<Comment> findAll(PageRequest of) {
        return null;
    }
}