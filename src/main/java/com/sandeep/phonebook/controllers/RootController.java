package com.sandeep.phonebook.controllers;


import com.sandeep.phonebook.entities.UserEntity;
import com.sandeep.phonebook.helper.Helper;
import com.sandeep.phonebook.services.IUserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserService userService;

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        if (authentication == null) {
            return;
        }
        System.out.println("Adding logged in user information to the model");
        String username = Helper.getUserName(authentication);
        logger.info("User logged in: {}", username);
        // database se data ko fetch : get user from db :
        UserEntity user = userService.getUserByEmail(username);
        model.addAttribute("loggedInUser", user);

    }
}
