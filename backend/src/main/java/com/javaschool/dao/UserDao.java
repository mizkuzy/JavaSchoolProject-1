package com.javaschool.dao;

import com.javaschool.model.User;
import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by bugav on 30.09.2017.
 */
@Component
public class UserDao extends AbstractDao<User> {

    @Override
    public void saveEntity(User entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    @Override
    public List<User> findAllEntities() {
        em.getTransaction().begin();
        TypedQuery<User> userTypedQuery = em.createQuery("SELECT u FROM User u", User.class);
        List<User> users = userTypedQuery.getResultList();
        em.getTransaction().commit();

        return users;
    }

    public User findUserByNameAndLastNameAndPassword(String name, String lastname, String password) {
        TypedQuery<User> userTypedQuery = em.createQuery("SELECT u FROM User u WHERE u.name=:name AND u.lastName=:lastName AND u.password=:password", User.class);
        userTypedQuery.setParameter("name", name);
        userTypedQuery.setParameter("lastName", lastname);
        userTypedQuery.setParameter("password", password);

        em.getTransaction().begin();
        User user = userTypedQuery.getSingleResult();
        em.getTransaction().commit();
        return user;

    }

    @Override
    public void removeEntity(User user) {
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
    }


    @Override
    public void deleteAllEntites() {
        TypedQuery<User> userTypedQuery = em.createQuery("DELETE FROM User u", User.class);

    }
}