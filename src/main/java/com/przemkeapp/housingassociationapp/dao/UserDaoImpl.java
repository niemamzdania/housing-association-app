package com.przemkeapp.housingassociationapp.dao;

import com.przemkeapp.housingassociationapp.Entity.Address;
import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.Entity.UserDetail;
import com.przemkeapp.housingassociationapp.exceptionhandling.UserNotFoundException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {

    private final EntityManager entityManager;

    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findUserByUsername(String username) {

        Session currentSession = entityManager.unwrap(Session.class);

        Query<User> theQuery = currentSession.createQuery("from User where id=:theUsername");
        theQuery.setParameter("theUsername", username);

        return theQuery.getSingleResult();
    }

    @Override
    public List<User> findAllUsers() {

        Session currentSession = entityManager.unwrap(Session.class);

        Query<User> theQuery = currentSession.createQuery("from User", User.class);

        return theQuery.getResultList();
    }

    @Override
    public void saveUser(User user) {

        Session currentSession = entityManager.unwrap(Session.class);

        currentSession.saveOrUpdate(user);
    }

    @Override
    public void deleteUserByUsername(String username) {

        Session currentSession = entityManager.unwrap(Session.class);

        Query theQuery = currentSession.createQuery("delete from User where userName=:theUsername");
        theQuery.setParameter("theUsername", username);

        theQuery.executeUpdate();
    }

    @Override
    public void saveUserAddress(Address address, String username) {

        Session currentSession = entityManager.unwrap(Session.class);

        User theUser = currentSession.get(User.class, username);

        theUser.getUserDetail().setAddress(address);

        currentSession.saveOrUpdate(theUser);
    }
}
