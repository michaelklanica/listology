package com.michaelklanica.listology.controllers;

import com.michaelklanica.listology.models.User;
import com.michaelklanica.listology.repos.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private UserRepository usersDao;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository usersDao, PasswordEncoder passwordEncoder) {
        this.usersDao = usersDao;
        this.passwordEncoder = passwordEncoder;
    }

    //  SHOW USER INDEX
    @GetMapping("/user/all")
    public String showUserIndex(Model viewModel){
        viewModel.addAttribute("users", usersDao.findAll());
        return "user/index";
    }

    //  SHOW USER PROFILE
    @GetMapping("/user/{id}")
    public String showUserProfile(@PathVariable long id, Model viewModel){
        viewModel.addAttribute("user", usersDao.getOne(id));
        return "user/profile";
    }

    //  SHOW USER EDIT FORM
    @GetMapping("/user/{id}/edit")
    public String showEditUserForm(@PathVariable long id, Model viewModel) {
        viewModel.addAttribute("user", usersDao.getOne(id));
        return "user/edit";
    }

    //  SUBMIT USER EDIT FORM
    @PostMapping("/user/{id}/edit")
    public String submitEditUserForm(@PathVariable long id, User userToBeSaved) {
        usersDao.save(userToBeSaved);
        return "redirect:/" + id;
    }

    //  SHOW USER REGISTRATION FORM
    @GetMapping("/register")
    public String showNewUserForm(Model viewModel) {
        viewModel.addAttribute("user", new User());
        return "user/register";
    }

    //  SUBMIT USER REGISTRATION FORM
    @PostMapping("/register")
    public String submitEditUserForm(@ModelAttribute User userToBeSaved) {
        String hash = passwordEncoder.encode(userToBeSaved.getPassword());
        userToBeSaved.setPassword(hash);
        usersDao.save(userToBeSaved);
        return "redirect:/login";
    }

    //  DELETE USER
    @PostMapping("/user/{id}/delete")
    public String deleteUser(@PathVariable long id) {
        usersDao.deleteById(id);
        return "user/index";
    }


}
