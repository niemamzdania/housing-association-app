package com.przemkeapp.housingassociationapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {
    @GetMapping("/")
    public String homePage() {
        return "home";
    }
}
