package com.sandeep.phonebook.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import com.sandeep.phonebook.entities.UserEntity;
import com.sandeep.phonebook.helper.Color;
import com.sandeep.phonebook.helper.Message;
import com.sandeep.phonebook.repositories.IUserRepository;
import com.sandeep.phonebook.requestDto.UserFormReqDTO;
import com.sandeep.phonebook.services.IUserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@Controller

@AllArgsConstructor
public class PageController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserRepository userRepository;

    @GetMapping("/")
    public String getIndex() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        return "home";
    }

    //about route
    @RequestMapping("/about")
    public String aboutPage() {
        System.out.println("about page loading boss ");
        return "about";
    }

    //service
    @RequestMapping("/services")
    public String servicePage() {
        System.out.println("service page loading boss ");
        return "services";
    }

    //Contact
    @RequestMapping("/contact")
    public String contact() {
        System.out.println("service page loading boss ");
        return "contact";
    }

    // login
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

//    @PostMapping("/login")
//    public String login(String s){
//        return "login";
//    }


    // Register
    @GetMapping("/register")
    public String register(Model model) {
        UserFormReqDTO userFormReqDTO = new UserFormReqDTO();
        model.addAttribute("userFormReqDTO", userFormReqDTO);
        return "register";
    }


   //  processing register
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(Model model, @Valid @ModelAttribute(name = "userFormReqDTO") UserFormReqDTO userFormReqDTO, BindingResult result, HttpSession session) {

        Message message;
        
        if (result.hasErrors()) {
            return "register";
        }
        UserEntity user = new UserEntity();

        user.setName(userFormReqDTO.getName());
        user.setEmail((userFormReqDTO.getEmail()));
        user.setPassword(userFormReqDTO.getPassword());
        user.setAbout(userFormReqDTO.getAbout());
        user.setPhoneNumber(userFormReqDTO.getPhoneNumber());
        user.setProfilePicLink("https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-sample-grunge-red-round-stamp.jpg");

        if (!userRepository.existsByEmail(userFormReqDTO.getEmail())) {
            userService.saveUser(user);
            message = Message.builder()
                    .content("Registration Successfully Bosss....")
                    .color(Color.blue)
                    .build();
            System.out.println("User Saved Successfully..... id>" + userFormReqDTO.getEmail());
            System.out.println("User Saved Successfully..... pass>" + userFormReqDTO.getPassword());
        } else {
            message = Message.builder().content("User already exist with same email id !").color(Color.red).build();
        }


        session.setAttribute("message", message);


        return "redirect:/register";
    }


}
