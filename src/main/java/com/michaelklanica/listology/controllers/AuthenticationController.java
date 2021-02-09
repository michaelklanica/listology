package com.michaelklanica.listology.controllers;

import com.michaelklanica.listology.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {
    @Autowired
    private UserService userServ;

    @GetMapping("/login")
    public String showLoginForm() {
        if(userServ.isLoggedIn()){
            return "redirect:/post/all";
        }
        return "sessions/new";
    }
}
