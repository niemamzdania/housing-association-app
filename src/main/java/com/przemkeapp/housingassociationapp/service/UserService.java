package com.przemkeapp.housingassociationapp.service;

import com.przemkeapp.housingassociationapp.Entity.Address;
import com.przemkeapp.housingassociationapp.Entity.Community;
import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.Entity.UserDetail;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

public interface UserService {
    User findUserByUsername(String username);
    List<String> findAllUsernames();
    void saveUserData(User user, String currentUsername);
    void deleteUserByUsername(String username);
    Set<String> findRolesByUsername(String username);
    void changeProfilePhoto(MultipartFile photo, String username) throws IOException;
    InputStream findPhotoByUsername(String username);
    void saveUserPersonalData(String username, UserDetail userDetail, Address userAddress);
    void registerUser(User user, UserDetail userDetail);
    List<Community> findAllCommunities();
}