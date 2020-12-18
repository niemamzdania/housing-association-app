package com.przemkeapp.housingassociationapp.service;

import com.przemkeapp.housingassociationapp.Entity.Address;
import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.dao.UserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    @Transactional
    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username) {
        userDao.deleteUserByUsername(username);
    }

    @Override
    @Transactional
    public void saveUserAddress(Address address, String username) {
        userDao.saveUserAddress(address, username);
    }
}
