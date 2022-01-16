package com.example.rozetka_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.rozetka_app.models.Like;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>{
    Like findLikeByUserIdEqualsAndVideoIdEquals(Long userId, Long videoId);
}