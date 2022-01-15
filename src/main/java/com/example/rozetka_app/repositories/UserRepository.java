package com.example.rozetka_app.repositories;

import com.example.rozetka_app.models.AppUser;
import com.example.rozetka_app.models.Statistics;
import org.apache.catalina.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<AppUser> {

    public AppUser findByUsername(String username){
        return null;
    }

    public AppUser findUserById(Long id){
        return null;
    }

    @Override
    public void save(AppUser object) {

    }

    @Override
    public Optional<AppUser> findById(Long entityId) {
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
    public List<AppUser> findAll(PageRequest of) {
        return null;
    }
}