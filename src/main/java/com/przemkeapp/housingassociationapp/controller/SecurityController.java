package com.przemkeapp.housingassociationapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SecurityController {

    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        return "access-denied";
    }

    @GetMapping("/afterLogin")
    public String showPageAfterLogin(RedirectAttributes attributes) {
        attributes.addFlashAttribute("successAlert", "You have been signed in successfully!");
        return "redirect:/";
    }

    @GetMapping("/afterLogout")
    public String showPageAfterLogout(RedirectAttributes attributes) {
        attributes.addFlashAttribute("successAlert", "You have been signed out successfully!");
        return "redirect:/";
    }
}
