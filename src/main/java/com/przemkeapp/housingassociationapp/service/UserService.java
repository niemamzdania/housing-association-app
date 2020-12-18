package com.przemkeapp.housingassociationapp.service;

import com.przemkeapp.housingassociationapp.Entity.Address;
import com.przemkeapp.housingassociationapp.Entity.User;

import java.util.List;

public interface UserService {
    User findUserByUsername(String username);
    List<User> findAllUsers();
    void saveUser(User user);
    void deleteUserByUsername(String username);
    void saveUserAddress(Address address, String username);
}