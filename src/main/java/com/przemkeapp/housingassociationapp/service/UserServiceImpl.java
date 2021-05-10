package com.przemkeapp.housingassociationapp.service;

import com.przemkeapp.housingassociationapp.Entity.Address;
import com.przemkeapp.housingassociationapp.Entity.Community;
import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.Entity.UserDetail;
import com.przemkeapp.housingassociationapp.dao.UserDao;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public List<String> findAllUsernames() {
        return userDao.findAllUsernames();
    }

    @Override
    public void saveUserData(User user, String currentUsername) {
        if (user.getCommunity().getId() == null) {
            user.setCommunity(null);
        }
        User tempUser = userDao.findUserByUsername(currentUsername);
        user.setPassword(tempUser.getPassword());

        if (!tempUser.getUserName().equals(user.getUserName()) || !tempUser.getEmail().equals(user.getEmail())) {
            user.changeUserDetail(tempUser.getUserDetail());
            userDao.deleteUserByUsername(currentUsername);
            userDao.saveUser(user);
        } else {
            tempUser.changeData(user);
            userDao.saveUser(tempUser);
        }
    }

    @Override
    public void changeProfilePhoto(MultipartFile photo, String username) throws IOException {
        User user = userDao.findUserByUsername(username);
        InputStream inputStream = new BufferedInputStream(photo.getInputStream());
        byte[] targetArray = new byte[inputStream.available()];
        inputStream.read(targetArray);
        user.getUserDetail().setPhoto(targetArray);
        userDao.saveUser(user);
    }

    @Override
    public InputStream findPhotoByUsername(String username) {
        User user = userDao.findUserByUsername(username);
        return new ByteArrayInputStream(user.getUserDetail().getPhoto());
    }

    @Override
    public void deleteUserByUsername(String username) {
        userDao.deleteUserByUsername(username);
    }

    @Override
    public Set<String> findRolesByUsername(String username) {
        List<String> tempRoles = userDao.findRolesBuUsername(username);
        return new HashSet<>(tempRoles);
    }

    @Override
    public void saveUserPersonalData(String username, UserDetail userDetail, Address userAddress) {
        User user = userDao.findUserByUsername(username);
        userDetail.setAddress(userAddress);
        user.changeUserDetail(userDetail);
        userDao.saveUser(user);
    }

    @Override
    public void registerUser(User user, UserDetail userDetail) {
        String encryptedPassword = "{bcrypt}" + new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setEnabled(true);
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        user.setRoles(roles);
        user.setUserDetail(userDetail);
        user.getUserDetail().setAddress(new Address());
        userDao.saveUser(user);
    }

    @Override
    public List<Community> findAllCommunities() {
        return userDao.findAllCommunities();
    }

    @Override
    public boolean checkPassword(String currentPassword) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User tempUser = userDao.findUserByUsername(username);
        StringBuilder userEncodedPassword = new StringBuilder();
        for (int i = 8; i < tempUser.getPassword().length(); i++) {
            userEncodedPassword.append(tempUser.getPassword().charAt(i));
        }
        return new BCryptPasswordEncoder().matches(currentPassword, userEncodedPassword.toString());
    }

    @Override
    public void changePassword(String newPassword) {
        newPassword = new BCryptPasswordEncoder().encode(newPassword);
        newPassword = "{bcrypt}" + newPassword;
        User user = userDao.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setPassword(newPassword);
        userDao.saveUser(user);
    }
}
