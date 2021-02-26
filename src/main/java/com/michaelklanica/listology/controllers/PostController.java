package com.michaelklanica.listology.controllers;

import com.michaelklanica.listology.models.Comment;
import com.michaelklanica.listology.models.Post;
import com.michaelklanica.listology.models.User;
import com.michaelklanica.listology.repos.CommentRepository;
import com.michaelklanica.listology.repos.PostRepository;
import com.michaelklanica.listology.repos.UserRepository;
import com.michaelklanica.listology.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private final PostRepository postDao;

    @Autowired
    private final UserRepository userDao;

    @Autowired
    private final CommentRepository commentDao;

    @Autowired
    private UserService userServ;

    public PostController(PostRepository postDao, UserRepository userDao, CommentRepository commentDao) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.commentDao = commentDao;
    }

    //  SHOW POST INDEX
    @GetMapping("/post/all")
    public String showPostIndex(Model viewModel){
        viewModel.addAttribute("musicposts", postDao.findAllByCategory("music"));
        viewModel.addAttribute("movieposts", postDao.findAllByCategory("movies"));
        viewModel.addAttribute("bookposts", postDao.findAllByCategory("books"));
        viewModel.addAttribute("otherposts", postDao.findAllByCategory("other"));
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
        return "redirect:/post/" + id;
    }

    //  SHOW POST CREATE FORM
    @GetMapping("/post/new")
    public String showNewPostForm(Model viewModel) {
        viewModel.addAttribute("post", new Post());
        viewModel.addAttribute("user", userServ.loggedInUser());
        return "post/new";
    }

    //  SUBMIT POST CREATE FORM
    @PostMapping("/post/new")
    public String submitNewPostForm(@Valid @ModelAttribute Post postToBeSaved, Errors validation, Model viewModel) {
        // POST MODEL VALIDATIONS
        if (validation.hasErrors()) {
            viewModel.addAttribute("errors", validation);
            viewModel.addAttribute("post", postToBeSaved);
            viewModel.addAttribute("user", userServ.loggedInUser());
            return "post/new";
        }

        User currentUser = userServ.loggedInUser();
        postToBeSaved.setAuthor(currentUser);
        Post currentPost = postDao.save(postToBeSaved);

        return "redirect:/user/"+currentUser.getId();
    }

    //  DELETE POST
    @PostMapping("/post/{id}/delete")
    public String deletePost(@PathVariable long id) {
        postDao.deleteById(id);
        return "post/index";
    }

    //  SHOW COMMENT CREATE FORM
    @GetMapping("/post/{id}/comment")
    public String showNewCommentForm(@PathVariable long id, Model viewModel) {
        viewModel.addAttribute("comment", new Comment());
        viewModel.addAttribute("postId", id);
        viewModel.addAttribute("user", userServ.loggedInUser());
        return "post/show";
    }

    //  SUBMIT POST COMMENT FORM
    @PostMapping("/post/{id}/comment")
    public String submitPostCommentForm(@PathVariable long id, Comment commentToBeSaved) {
        User userDb = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commentToBeSaved.setUser(userDb);
        commentDao.save(commentToBeSaved);
        return "post/" + id;
    }

}
