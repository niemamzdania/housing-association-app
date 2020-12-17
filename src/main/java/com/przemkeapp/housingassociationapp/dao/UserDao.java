package com.przemkeapp.housingassociationapp.dao;

import com.przemkeapp.housingassociationapp.Entity.User;

import java.util.List;

public interface UserDao {
    User findUserByUsername(String username);
    List<User> findAllUsers();
    void saveUser(User user);
    void deleteUserById(int userId);
}
