package com.przemkeapp.housingassociationapp.dao;

import com.przemkeapp.housingassociationapp.Entity.Address;
import com.przemkeapp.housingassociationapp.Entity.User;

import java.util.List;
import java.util.Set;

public interface UserDao {
    User findUserByUsername(String username);
    List<User> findAllUsers();
    List<String> findAllUsernames();
    void saveUser(User user);
    void saveUserData(User user);
    void deleteUserByUsername(String username);
    void saveUserAddress(Address address, String username);
    boolean checkIfUnique(String fieldName, String value);
    List<String> findRolesBuUsername(String username);
}
