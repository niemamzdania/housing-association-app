package com.przemkeapp.housingassociationapp.dao;

import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.exceptionhandling.UserNotFoundException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final EntityManager entityManager;

    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findUserByUsername(String username) {

        Session session = entityManager.unwrap(Session.class);

        Query query = session.createQuery("from User where userName=:theUsername");
        query.setParameter("theUsername", username);

        return (User) query.getSingleResult();
    }

    @Override
    public List<User> findAllUsers() {
        return null;
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public void deleteUserById(int id) {

    }
}
