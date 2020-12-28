package com.przemkeapp.housingassociationapp.controller;

import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.Entity.UserDetail;
import com.przemkeapp.housingassociationapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/form")
    public String showRegistrationForm(Model model) {

        model.addAttribute("user", new User());
        model.addAttribute("userDetail", new UserDetail());

        return "register/registration-form";
    }

    @PostMapping("/save")
    public String registrationFormProcess(@Valid User user, BindingResult bindingResultForUser,
            @Valid UserDetail userDetail, BindingResult bindingResultForUserDetail) {

        if(bindingResultForUser.hasErrors() || bindingResultForUserDetail.hasErrors()) {
            return "register/registration-form";
        }

        userService.registerUser(user, userDetail);

        return "redirect:/";
    }


}
