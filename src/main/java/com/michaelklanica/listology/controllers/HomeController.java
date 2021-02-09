package com.michaelklanica.listology.controllers;

import com.michaelklanica.listology.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private UserService userServ;

    @GetMapping("/")
    public String showHomepage(Model model) {
        if(userServ.isLoggedIn()){
            return "redirect:/dashboard";
        }
        return "index";
    }

}
