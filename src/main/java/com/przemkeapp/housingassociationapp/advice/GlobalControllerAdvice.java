package com.przemkeapp.housingassociationapp.advice;

import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UserService userService;

    @ModelAttribute("currentUser")
    public User getCurrentUser() {

        User currentUser = null;

        if (isAuthenticated()) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            currentUser = userService.findUserByUsername(username);
        }

        return currentUser;
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
