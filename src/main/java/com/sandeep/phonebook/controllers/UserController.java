package com.sandeep.phonebook.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sandeep.phonebook.services.IUserService;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private IUserService userService ;


    //user dashboard page
    @RequestMapping(value = "/dashboard")
    public String userDashboard(Authentication authentication) {
        System.out.println("User dashboard");
        return "user/dashboard";
    }

    //user profile page
    @GetMapping("/profile")
    public String userProfile(Model model, Authentication authentication) {
        System.out.println("Profile dashboard");

        return "user/profile";
    }

    // user add contact page

    // user view contact page

    // user edit contact page

    // user delete contact page

}
