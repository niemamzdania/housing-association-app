package com.przemkeapp.housingassociationapp.controller;

import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {

    /*private UserDetailsService userDetailsService;

    public HomeController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }*/

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/hello")
    public String helloWorld(Principal principal) {

        /*
        System.out.println("----------------++++++++++++++++++");

        Set<String> u1Roles = new HashSet<>();
        u1Roles.add("ROLE_USER");
        u1Roles.add("ROLE_ADMIN");
        String password = new BCryptPasswordEncoder().encode("qwerty");
        User u1 = new User("janek", "{bcrypt}" + password, true, u1Roles);
        userRepository.save(u1);

        System.out.println("======>>>>>>>" + principal.toString());

        //UserDetails u2 = userDetailsService.loadUserByUsername("testuser");
        //System.out.println("+++++++" + u2.getUsername());
        */

        return "home";
    }
}
