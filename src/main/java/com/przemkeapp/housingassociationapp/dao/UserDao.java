package com.przemkeapp.housingassociationapp.dao;

import com.przemkeapp.housingassociationapp.Entity.Address;
import com.przemkeapp.housingassociationapp.Entity.Comment;
import com.przemkeapp.housingassociationapp.Entity.Community;
import com.przemkeapp.housingassociationapp.Entity.User;

import java.util.List;

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
    List<Community> findAllCommunities();
    void saveComment(Comment comment);
}
