package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
 @RequestMapping("/")
public String homeContrller(Model model){
    model.addAttribute("name", "Sandeep kumar ");
    return"home";
}
}
