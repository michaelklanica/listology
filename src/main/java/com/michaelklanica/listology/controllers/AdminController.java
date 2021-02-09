package com.michaelklanica.listology.controllers;

import com.michaelklanica.listology.models.*;
import com.michaelklanica.listology.repos.*;
import com.michaelklanica.listology.services.PostService;
import com.michaelklanica.listology.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AdminController {

    @Autowired
    private UserService userServ;

    @Autowired
    private UserRepository userDao;

    @Autowired
    private PostRepository postDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PostService postServ;

    @GetMapping("/admin")
    public String getDashboard(Model model) {
        User currentUser = userServ.loggedInUser();
        if(currentUser == null || !currentUser.getAdmin()){
            return "/error/403";
        }
        model.addAttribute("currentUser",currentUser);
        userServ.setAdminDash(model);
        return "users/admin/index";
    }

    @PostMapping("/admin/users/new")
    public String createUser(@Valid @ModelAttribute User user,
                             Errors validation,
                             Model model
    ) {
        // validate if email already exists in db
        User existingEmail = userDao.getFirstByEmail(user.getEmail());
        if(existingEmail != null){
            validation.rejectValue("email", "user.email", "Duplicate email " + user.getEmail());
        }
        // user model validations
        if (validation.hasErrors()) {
            userServ.setAdminDash(model);
            model.addAttribute("errors", validation);
            model.addAttribute("userModel", user);
            return "users/admin/index";
        }
        // encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // add fields to the existing user
        user.setAdmin(false);
        // save th user to db
        userDao.save(user);

        return "redirect:/admin";
    }

    @PostMapping("/admin/users/{id}/grant")
    public String makeAdmin(@PathVariable long id){
        User dbUser = userDao.getOne(id);
        if (dbUser.getAdmin()) {
            dbUser.setAdmin(false);
        } else {
            dbUser.setAdmin(true);
        }
        userDao.save(dbUser);
        return "redirect:/admin";
    }

    @PostMapping("/admin/users/{id}/delete")
    public String deleteUser(@PathVariable long id){
        User user = userDao.getOne(id);
        userServ.deleteUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/posts/new")
    public String createPost( @Valid @ModelAttribute Post post,
                                Errors validation,
                                Model model
    ) {
        if (validation.hasErrors()) {
            model.addAttribute("errors", validation);
            model.addAttribute("postModel", post);
            return "post/new";
        }
        postDao.save(post);
        return "redirect:/admin";
    }

    @PostMapping("/admin/posts/{id}/delete")
    public String deleteRecipe(@PathVariable long id){
        postServ.deletePost(postDao.getOne(id));
        return "redirect:/admin";
    }

}