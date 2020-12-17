package com.przemkeapp.housingassociationapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorHandlingController {

    @GetMapping("/error")
    public String showErrorPage(ModelAndView modelAndView) {
        return modelAndView.getViewName();
    }
}
