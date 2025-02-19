package com.sandeep.phonebook.controllers;

import com.sandeep.phonebook.entities.UserEntity;
import com.sandeep.phonebook.helper.Color;
import com.sandeep.phonebook.helper.Message;
import com.sandeep.phonebook.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class EmailVarificationAuthController {

    // verify email

    @Autowired
    private IUserRepository userRepo;

    @GetMapping("/verify-email")
    public String verifyEmail(
            @RequestParam("token") String token, HttpSession session) {

        UserEntity user = userRepo.findByEmailToken(token).orElse(null);

        if (user != null) {
            // user fetch hua hai :: process karna hai

            if (user.getEmailToken().equals(token)) {
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepo.save(user);
                session.setAttribute("message", Message.builder()
                        .color(Color.green)
                        .content("You email is verified. Now you can login  ")
                        .build());
                return "success_page";
            }

            session.setAttribute("message", Message.builder()
                    .color(Color.red)
                    .content("Email not verified ! Token is not associated with user .")
                    .build());
            return "error_page";

        }

        session.setAttribute("message", Message.builder()
                .color(Color.red)
                .content("Email not verified ! Token is not associated with user .")
                .build());

        return "error_page";
    }

}
