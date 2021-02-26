package com.michaelklanica.listology.controllers;

import com.michaelklanica.listology.models.Comment;
import com.michaelklanica.listology.repos.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/comments")
    @ResponseBody
    public List<Comment> getComments(){
        return commentRepository.findAll();
    }



    @GetMapping("/comments/children/{id}")
    @ResponseBody
    public List<Comment> getChildren(@PathVariable long id){
        return commentRepository.findByParent(commentRepository.getOne(id));
    }

}
