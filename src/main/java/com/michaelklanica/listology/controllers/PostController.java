package com.michaelklanica.listology.controllers;

import com.michaelklanica.listology.models.Post;
import com.michaelklanica.listology.models.User;
import com.michaelklanica.listology.repos.PostRepository;
import com.michaelklanica.listology.repos.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    private final PostRepository postDao;
    private final UserRepository userDao;

    public PostController(PostRepository postDao, UserRepository userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
    }

    //  SHOW POST INDEX
    @GetMapping("/post/all")
    public String showPostIndex(Model viewModel){
        viewModel.addAttribute("posts", postDao.findAll());
        return "post/index";
    }

    //  SHOW POST
    @GetMapping("/post/{id}")
    public String showPost(@PathVariable long id, Model viewModel){
        viewModel.addAttribute("post", postDao.getOne(id));
        Post post = postDao.getOne(id);
        String username = post.getAuthor().getUsername();
        viewModel.addAttribute("username", username);
        return "post/show";
    }

    //  SHOW POST EDIT FORM
    @GetMapping("/post/{id}/edit")
    public String showEditPostForm(@PathVariable long id, Model viewModel) {
        viewModel.addAttribute("post", postDao.getOne(id));
        return "post/edit";
    }

    //  SUBMIT POST EDIT FORM
    @PostMapping("/post/{id}/edit")
    public String submitEditPostForm(@PathVariable long id, Post postToBeSaved) {
        User userDb = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postToBeSaved.setAuthor(userDb);
        postDao.save(postToBeSaved);
        return "redirect:/" + id;
    }

    //  SHOW POST CREATE FORM
    @GetMapping("/post/new")
    public String showNewPostForm(Model viewModel) {
        viewModel.addAttribute("post", new Post());
        return "post/new";
    }

    //  SUBMIT POST CREATE FORM
    @PostMapping("/post/new")
    public String submitEditPostForm(@ModelAttribute Post postToBeSaved) {
        User userDb = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postToBeSaved.setAuthor(userDb);
        // THIS IS A GOOD PLACE TO ADD ANY RELATED DATA
        postDao.save(postToBeSaved);
        return "post/index";
    }

    //  DELETE POST
    @PostMapping("/post/{id}/delete")
    public String deletePost(@PathVariable long id) {
        postDao.deleteById(id);
        return "post/index";
    }

}
