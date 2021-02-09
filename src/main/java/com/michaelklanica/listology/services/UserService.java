package com.michaelklanica.listology.services;

import com.michaelklanica.listology.models.*;
import com.michaelklanica.listology.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    UserRepository userDao;

    @Autowired
    private PostRepository postDao;

    @Autowired
    private FavoriteRepository favDao;

    @Autowired
    private CommentRepository commentDao;

    @Autowired
    private PostService postServ;


    public boolean isLoggedIn() {
        boolean isAnonymousUser =
                SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
        return ! isAnonymousUser;
    }

    // Returns a user obj from db if spring security  session not anonymous
    public User loggedInUser() {
        if (! isLoggedIn()) {
            return null;
        }
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDao.getOne(sessionUser.getId());
    }

    // Checks if the user is the owner of the recipe
    public boolean isAuthor(User user){
        boolean isAuthor = false;
        isAuthor = isLoggedIn() && (user.getId() == loggedInUser().getId());
        if( loggedInUser().getAdmin()){
            isAuthor = true;
        }
        return  isAuthor;
    }

    // if the user is logged in and is the profile owner allow edit
    public boolean canEditProfile(User profileUser){
        return isLoggedIn() && (profileUser.getId() == loggedInUser().getId());
    }

    // Authenticates the user via spring security
    public void authenticate(User user) {
        UserDetails userDetails = new UserWithRoles(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(auth);
    }

    // Set admin dashboard
    public Model setAdminDash(Model model){
        model.addAttribute("users",userDao.findAll());
        model.addAttribute("userModel",new User());


        return model;
    }

    // delete user

    @Transactional
    public void deleteUser(User user){
        for( Post post : user.getPosts()){
            postServ.deletePost(post);
        }
        favDao.deleteAllByUser(user);
        userDao.save(user);
        userDao.delete(user);
    }
}
