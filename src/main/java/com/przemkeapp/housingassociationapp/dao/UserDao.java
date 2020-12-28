package com.przemkeapp.housingassociationapp.dao;

import com.przemkeapp.housingassociationapp.Entity.Address;
import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.Entity.UserDetail;

import java.util.List;

public interface UserDao {
    User findUserByUsername(String username);
    List<User> findAllUsers();
    void saveUser(User user);
    void deleteUserByUsername(String username);
    void saveUserAddress(Address address, String username);
}
