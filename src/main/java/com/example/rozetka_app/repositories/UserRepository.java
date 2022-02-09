package com.example.rozetka_app.repositories;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.example.rozetka_app.models.BaseUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.rozetka_app.models.AppUser;

@Repository
public class UserRepository extends BaseRepository<AppUser> {
    private final PasswordEncoder passwordEncoder;

    public UserRepository(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AppUser findByUsername(String username){
        try{
            CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<AppUser> criteriaQuery = criteriaBuilder.createQuery(AppUser.class);
            Root<AppUser> appUserRoot = criteriaQuery.from(AppUser.class);
            Predicate usernameEqual = criteriaBuilder.equal(appUserRoot.get("username"), username);

            criteriaQuery.where(usernameEqual);

            TypedQuery<AppUser> typedQuery = this.entityManager.createQuery(criteriaQuery);

            return typedQuery.getSingleResult();
        } catch (Throwable throwable) {
            return null;
        }
    }

    @Transactional
    public Page<BaseUser> findUsersWithHiddenProps(PageRequest pageRequest) {
        int page = pageRequest.getPageNumber();
        int total = pageRequest.getPageSize();

        Query query = this.entityManager.createQuery("from User", BaseUser.class);
        query.setMaxResults(total);
        query.setFirstResult(page * total);

        long size = Long.parseLong(this.entityManager
                .createQuery("select count(*) from User")
                .getSingleResult().toString());

        return (Page<BaseUser>) new PageImpl(query.getResultList(), pageRequest, size);
    }

    @Transactional
    public AppUser findUserById(Long id){
        return this.entityManager.find(AppUser.class, id);
    }

    @Transactional
    @Override
    public void save(AppUser object) {
        object.setPassword(passwordEncoder.encode(object.getPassword()));

        if (object.getId() == null) {
            this.entityManager.persist(object);
        } else {
            this.entityManager.merge(object);
        }
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.entityManager.remove(findUserById(id));
    }

    @Transactional
    @Override
    public List<AppUser> findAll(Sort id) {
        throw new IllegalArgumentException();
    }

    @Transactional
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