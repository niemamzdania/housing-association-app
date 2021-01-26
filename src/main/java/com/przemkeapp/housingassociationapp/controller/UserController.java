package com.przemkeapp.housingassociationapp.controller;

import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    @PreAuthorize("#username == authentication.name || hasRole('ADMIN')")
    public String showUser(@RequestParam("username") String username, Model model) {
        User user = userService.findUserByUsername(username);

        model.addAttribute("user", user);

        return "users/show-user";
    }

    /*@GetMapping("/edit")
    @PreAuthorize("#username == authentication.name")
    public String editUser(@RequestParam("username") String username, Model model) {
        User user = userService.findUserByUsername(username);

        model.addAttribute("user", user);

        return null;
    }*/
}
