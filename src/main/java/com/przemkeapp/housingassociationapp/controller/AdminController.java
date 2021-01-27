package com.przemkeapp.housingassociationapp.controller;

import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/usersList")
    @PreAuthorize("hasRole('ADMIN')")
    public String showUsernames(@RequestParam("username") String username, Model model) {

        List<String> usernames = userService.findAllUsernames();
        User user = userService.findUserByUsername(username);

        model.addAttribute("usernames", usernames);
        model.addAttribute("user", user);

        return "users/edit-users";
    }

    @PostMapping("/saveUserData")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveUserData(@Valid User userForm, BindingResult bindingResultForUser,
                               @RequestParam("currentUsername") String currentUsername) {

        for(ObjectError error : bindingResultForUser.getAllErrors()) {
            if(!Objects.equals(error.getCode(), "UniqueUserField")) {
                return "users/edit-users";
            }
        }

        userService.saveUserData(userForm, currentUsername);

        return "redirect:/admin/usersList?username=" + userForm.getUserName();
    }
}
