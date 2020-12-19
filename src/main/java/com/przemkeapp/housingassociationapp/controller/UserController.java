package com.przemkeapp.housingassociationapp.controller;

import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    @PreAuthorize("#username == authentication.name || hasRole('ADMIN')")
    public String showUser(@PathVariable("username") String username, Model model) {

        User user = userService.findUserByUsername(username);

        model.addAttribute("user", user);

        return "users/show-user";
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAllUsers(Model model) {
        List<User> userList = userService.findAllUsers();

        model.addAttribute("users", userList);

        return "users/show-all-users";
    }
}
