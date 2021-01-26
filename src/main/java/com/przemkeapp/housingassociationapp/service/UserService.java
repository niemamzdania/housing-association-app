package com.przemkeapp.housingassociationapp.service;

import com.przemkeapp.housingassociationapp.Entity.Address;
import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.Entity.UserDetail;

import java.util.List;

public interface UserService {
    User findUserByUsername(String username);
    List<User> findAllUsers();
    List<String> findAllUsernames();
    void saveUser(User user);
    void saveUserData(User user, String currentUsername);
    void deleteUserByUsername(String username);
    void saveUserAddress(Address address, String username);
    void registerUser(User user, UserDetail userDetail);
}