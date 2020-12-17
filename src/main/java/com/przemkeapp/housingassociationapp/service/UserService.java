package com.przemkeapp.housingassociationapp.service;

import com.przemkeapp.housingassociationapp.Entity.User;

import java.util.List;

public interface UserService {
    User findUserByUsername(String username);
    List<User> findAllUsers();
    void saveUser(User user);
    void deleteUserById(int userId);
}