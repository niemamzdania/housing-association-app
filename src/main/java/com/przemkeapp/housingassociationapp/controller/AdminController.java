package com.przemkeapp.housingassociationapp.controller;

import com.przemkeapp.housingassociationapp.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @NonNull
    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("username") String username,
                             HttpServletRequest request, Authentication auth) throws ServletException {

        log.trace("Starting to deleting user " + username);
        userService.deleteUserByUsername(username);

        if (auth.getName().equals(username)) {
            request.logout();
            return "redirect:/";
        }

        return "redirect:/user/editUser?username=" + auth.getName();
    }
}
