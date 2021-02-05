package com.michaelklanica.listology.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class PostController {

    //  SHOW POST INDEX
    @GetMapping("/all")
    public String showPostIndex(){
        return "post/index";
    }

    //  SHOW POST
    @GetMapping("/{id}")
    public String showPost(@PathVariable long id){
        return "post/show";
    }

    //  SHOW POST EDIT FORM
    @GetMapping("/{id}/edit")
    public String showEditPostForm(@PathVariable long id) { return "post/edit"; }

    //  SUBMIT POST EDIT FORM
    @PostMapping("/{id}/edit")
    public String submitEditPostForm(@PathVariable long id) { return "redirect:/" + id; }

    //  SHOW POST CREATE FORM
    @GetMapping("/new")
    public String showNewPostForm() { return "post/new"; }

    //  SUBMIT POST CREATE FORM
    @PostMapping("/new")
    public String submitEditPostForm() { return "post/index"; }

    //  DELETE POST
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable long id) {return "post/index"; }

}
