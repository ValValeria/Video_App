package com.example.rozetka_app.repositories;

import com.example.rozetka_app.models.Like;
import com.example.rozetka_app.models.Statistics;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

interface ILikeRepository{
    Like findLikeByUserIdEqualsAndVideoIdEquals(Long userId, Long videoId);
}
@Repository
public class LikeRepository extends BaseRepository<Like> implements ILikeRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Like findLikeByUserIdEqualsAndVideoIdEquals(Long userId, Long videoId) {
        return null;
    }

    @Override
    public void save(Like object) {

    }

    @Override
    public Optional<Like> findById(Long entityId) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Statistics> findAll(Sort id) {
        return null;
    }

    @Override
    public List<Like> findAll(PageRequest of) {
        return null;
    }
}