package com.example.rozetka_app.repositories;

import com.example.rozetka_app.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findLikeByUserIdEqualsAndVideoIdEquals(Long userId, Long videoId);
}
