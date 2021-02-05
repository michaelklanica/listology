package com.michaelklanica.listology.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    //  SHOW USER INDEX
    @GetMapping("/all")
    public String showUserIndex(){
        return "user/index";
    }

    //  SHOW USER PROFILE
    @GetMapping("/{id}")
    public String showUserProfile(@PathVariable long id){
        return "user/profile";
    }

    //  SHOW USER EDIT FORM
    @GetMapping("/{id}/edit")
    public String showEditUserForm(@PathVariable long id) { return "user/edit"; }

    //  SUBMIT USER EDIT FORM
    @PostMapping("/{id}/edit")
    public String submitEditUserForm(@PathVariable long id) { return "redirect:/" + id; }

    //  SHOW USER CREATE FORM
    @GetMapping("/{id}/new")
    public String showNewUserForm(@PathVariable long id) { return "user/new"; }

    //  SUBMIT USER CREATE FORM
    @PostMapping("/new")
    public String submitEditUserForm() { return "user/index"; }

    //  DELETE USER
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable long id) {return "user/index"; }


}
