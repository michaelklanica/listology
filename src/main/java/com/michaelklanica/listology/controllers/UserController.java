package com.michaelklanica.listology.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

//    SHOW ALL USERS
    @GetMapping("/all")
    public String showAllUsers(){
        return "user/index";
    }

//    SHOW ONE USER
    @GetMapping("/{id}")
    @ResponseBody
    public String showUser(@PathVariable long id){
        return "user " + id;
    }

//

}
