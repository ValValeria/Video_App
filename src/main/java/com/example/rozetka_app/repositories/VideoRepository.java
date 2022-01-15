package com.example.rozetka_app.repositories;

import java.util.List;
import java.util.Optional;

import com.example.rozetka_app.models.Statistics;
import com.example.rozetka_app.models.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

//    @Query("from Video v where v.user.username like :search or v.description like :search or v.title like :search")
@Repository
public class VideoRepository extends BaseRepository<Video>{
    public Page<Video> findAllByText(String search, Pageable pageable) {
        return null;
    }

    public Video findVideoById(Long id) {
        return null;
    }

    public void deleteVideoById(Long id) {
    }

    @Override
    public void save(Video object) {

    }

    public Optional<Video> findById(Long videoId) {
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
    public List<Video> findAll(PageRequest of) {
        return null;
    }
}