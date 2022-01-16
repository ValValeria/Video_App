package com.example.rozetka_app.repositories;

import java.util.List;

import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.example.rozetka_app.models.AppUser;

@Repository
public class UserRepository extends BaseRepository<AppUser> {
    public AppUser findByUsername(String username){
        return (AppUser) this.entityManager
                .createQuery("from User where username = :username")
                .setParameter("username", username)
                .getSingleResult();
    }

    public AppUser findUserById(Long id){
        return this.entityManager.find(AppUser.class, id);
    }

    @Override
    public void save(AppUser object) {
        if (object.getId() == null) {
            this.entityManager.persist(object);
        } else {
            this.entityManager.merge(object);
        }
    }

    @Override
    public void deleteById(Long id) {
        this.entityManager.remove(findUserById(id));
    }

    @Override
    public List<AppUser> findAll(Sort id) {
        throw new IllegalArgumentException();
    }

    @Override
    public Page<AppUser> findAll(PageRequest pageRequest) {
        int page = pageRequest.getPageNumber();
        int total = pageRequest.getPageSize();

        Query query = this.entityManager.createQuery("from User");
        query.setMaxResults(total);
        query.setFirstResult(page * total);

        Long size = Long.valueOf(this.entityManager
                .createQuery("select count(*) from User")
                .getSingleResult().toString());

        Page<AppUser> appUserPage = new PageImpl(query.getResultList(), pageRequest, size);

        return appUserPage;
    }
}