package com.michaelklanica.listology.services;

import com.michaelklanica.listology.models.*;
import com.michaelklanica.listology.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class PostService {
    @Autowired
    private PostRepository postDao;

    @Autowired
    private UserRepository userDao;

    @Autowired
    private UserService userServ;

    @Autowired FavoriteRepository favDao;

    @Transactional
    public void deletePost(Post post){
        favDao.deleteAllByPost(post);
        postDao.save(post);
        postDao.delete(post);
    }

}
