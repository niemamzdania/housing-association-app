package com.przemkeapp.housingassociationapp.dao;

import com.przemkeapp.housingassociationapp.Entity.Address;
import com.przemkeapp.housingassociationapp.Entity.Community;
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
    @Transactional
    public User findUserByUsername(String username) {

        Session currentSession = entityManager.unwrap(Session.class);

        Query<User> theQuery = currentSession.createQuery("from User where id=:theUsername");
        theQuery.setParameter("theUsername", username);

        return theQuery.getSingleResult();
    }

    @Override
    @Transactional
    public List<User> findAllUsers() {

        Session currentSession = entityManager.unwrap(Session.class);

        Query<User> theQuery = currentSession.createQuery("from User");

        return theQuery.getResultList();
    }

    @Override
    @Transactional
    public List<String> findAllUsernames() {

        Session currentSession = entityManager.unwrap(Session.class);

        Query<String> theQuery = currentSession.createQuery("SELECT U.userName FROM User U");

        return theQuery.getResultList();
    }

    @Override
    @Transactional
    public void saveUser(User user) {

        Session currentSession = entityManager.unwrap(Session.class);

        currentSession.saveOrUpdate(user);
    }

    @Override
    @Transactional
    public void saveUserData(User user) {

        Session currentSession = entityManager.unwrap(Session.class);

        currentSession.merge(user);
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username) {

        Session currentSession = entityManager.unwrap(Session.class);

        User tempUser = currentSession.get(User.class, username);

        currentSession.delete(tempUser);
    }

    @Override
    @Transactional
    public void saveUserAddress(Address address, String username) {

        Session currentSession = entityManager.unwrap(Session.class);

        User theUser = currentSession.get(User.class, username);

        theUser.getUserDetail().setAddress(address);

        currentSession.update(theUser);
    }

    @Override
    @Transactional
    public boolean checkIfUnique(String fieldName, String value) {

        Session currentSession = entityManager.unwrap(Session.class);

        String theQuerySyntax = "select U." + fieldName + " from User U";

        Query<String> theQuery = currentSession.createQuery(theQuerySyntax);

        for (Object tempFieldName : theQuery.getResultList()) {
            if (tempFieldName.equals(fieldName)) {
                return false;
            }
        }

        return true;
    }

    @Override
    @Transactional
    public List<String> findRolesBuUsername(String username) {

        Session currentSession = entityManager.unwrap(Session.class);

        Query theQuery = currentSession.createQuery
                ("select elements(U.roles) from User U where U.userName=:username");
        theQuery.setParameter("username", username);

        return theQuery.getResultList();
    }

    @Override
    @Transactional
    public List<Community> findAllCommunities() {

        Session currentSession = entityManager.unwrap(Session.class);

        Query<Community> theQuery = currentSession.createQuery("from Community");

        return theQuery.getResultList();
    }
}
