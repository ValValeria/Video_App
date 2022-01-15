package com.example.rozetka_app.repositories;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.example.rozetka_app.models.Statistics;
import com.example.rozetka_app.models.Comment;


@Repository
public class CommentRepository extends BaseRepository<Comment>{
    public Comment findCommentById(Long id) {
        return this.entityManager.find(Comment.class, id);
    }

    @Transactional
    @Override
    public void save(Comment object) {
       this.entityManager.merge(object);
    }

    @Transactional
    @Override
    public Optional<Comment> findById(Long id) {
        Comment comment = this.entityManager.find(Comment.class, id);
        Optional<Comment> optionalComment = Optional.empty();

        if (Objects.nonNull(comment)) {
            optionalComment = Optional.of(comment);
        }

        return Optional.empty();
    }

    @Transactional
    public void deleteById(Long id) {
        Query query = this.entityManager.createQuery("delete from Comment c where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
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