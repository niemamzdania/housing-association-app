package com.przemkeapp.housingassociationapp.controller;

import com.przemkeapp.housingassociationapp.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("username") String username,
                             HttpServletRequest request, Authentication auth) throws ServletException {

        userService.deleteUserByUsername(username);

        if(auth.getName().equals(username)) {
            request.logout();
            return "redirect:/";
        }

        return "redirect:/user/editUser?username=" + auth.getName();
    }
}
