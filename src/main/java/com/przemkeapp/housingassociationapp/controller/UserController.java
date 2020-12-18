package com.przemkeapp.housingassociationapp.controller;

import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    @PreAuthorize("#username == authentication.name || hasRole('ADMIN')")
    public String showUser(@PathVariable("username") String username, Model theModel) {

        User tempUser = userService.findUserByUsername(username);

        theModel.addAttribute("theUser", tempUser);

        return "users/show-user";
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAllUsers(Model theModel) {
        List<User> userList = userService.findAllUsers();

        theModel.addAttribute("users", userList);

        return "users/show-all-users";
    }


}
