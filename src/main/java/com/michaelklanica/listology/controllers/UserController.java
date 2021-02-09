package com.michaelklanica.listology.controllers;

import com.michaelklanica.listology.models.User;
import com.michaelklanica.listology.repos.UserRepository;
import com.michaelklanica.listology.services.PostService;
import com.michaelklanica.listology.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserRepository usersDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userServ;

    @Autowired
    private PostService postServ;

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
    public String showUserProfile(@PathVariable long id, Model model) {
        // logged in user
        User currentUser = userServ.loggedInUser();
        // profile owner
        User user = usersDao.findFirstById(id);
        // if profile page for user doesnt exist send to post index
        if(user == null){
            return "redirect:/post/all";
        }

        model.addAttribute("user", user);
        // check if logged in user is the profile owner
        model.addAttribute("isAuthor",userServ.isAuthor(user));


        return "user/profile";
    }

    // GET LOGGED IN USER DASHBOARD
    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        User currentUser = userServ.loggedInUser();
        if(currentUser.getAdmin()){
            return "redirect:/admin";
        }
        model.addAttribute("isFollowing", true);
        model.addAttribute("user", currentUser);
        model.addAttribute("isAuthor",true);

        return "user/profile";
    }

    //  SHOW USER EDIT FORM
    @GetMapping("/user/{id}/edit")
    public String showEditUserForm(@PathVariable long id, Model viewModel) {
        viewModel.addAttribute("user", usersDao.getOne(id));
        User user = usersDao.getOne(id);
        // restrict access to owner redirects others
        if(!userServ.isAuthor(user)){
            return "redirect:/post/all";
        }
        return "user/edit";
    }

    //  SUBMIT USER EDIT FORM
    @PostMapping("/user/{id}/edit")
    public String submitEditUserForm(
        @PathVariable(name="id") long id,
        @Valid User editUser,
        Errors validation,
        Model model) {

            User user = usersDao.getOne(id);
            // user model validations
            if (validation.hasErrors()) {
                model.addAttribute("errors", validation);
                model.addAttribute("user", editUser);
                return "user/edit";
            }
            // add fields to the existing user
            user.setUsername(editUser.getUsername());
            user.setEmail(editUser.getEmail());
            user.setAboutMe(editUser.getAboutMe());
            // submit update
            user = usersDao.save(user);
            model.addAttribute("user",user);
            return "redirect:/user/"+id;
        }

    //  SHOW USER REGISTRATION FORM
    @GetMapping("/register")
    public String showNewUserForm(Model viewModel) {
        if(userServ.isLoggedIn()){
            return "redirect:/post/all";
        }
        viewModel.addAttribute("user", new User());
        return "user/register";
    }

    //  SUBMIT USER REGISTRATION FORM
    @PostMapping("/register")
    public String submitEditUserForm(@ModelAttribute User userToBeSaved, Errors validation, Model model) {
        // validate if email already exists in db
        User existingEmail = usersDao.getFirstByEmail(userToBeSaved.getEmail());
        if(existingEmail != null){
            validation.rejectValue("email", "user.email",  "email already registered");
        }
        // user model validations
        if (validation.hasErrors()) {
            model.addAttribute("errors", validation);
            model.addAttribute("user", userToBeSaved);
            return "user/register";
        }
        // encrypt password
        userToBeSaved.setPassword(passwordEncoder.encode(userToBeSaved.getPassword()));
        // add fields to the existing user
        userToBeSaved.setAdmin(false);

        // save th user to db
        User dbUser = usersDao.save(userToBeSaved);

        // login the registered user
        userServ.authenticate(dbUser);
        model.addAttribute(dbUser);
        return "redirect:/user/"+dbUser.getId();
    }

    // UPDATE PASSWORD
    @PostMapping("/users/{id}/reset")
    public String resetPassword(@PathVariable(name="id") long id, @RequestParam Map<String, String> params, Model model){
        User user = usersDao.getOne(id);
        // regex for password
        String passValidation = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        String password = params.get("password");
        String newPassword = params.get("new_password");
        String passwordConfirmation = params.get("confirm_password");
        boolean canUpdate = true;
        // if new password and confirmation dont match set error
        if(!newPassword.equals(passwordConfirmation)){
            canUpdate = false;
            model.addAttribute("notMatch", true );
        }
        // if password fails regex set error
        if(!newPassword.matches(passValidation)){
            canUpdate = false;
            model.addAttribute("invalidPassword",true);
        }
        // if errors redirect to edit page
        if(!canUpdate){
            model.addAttribute("user",user);
            return "user/edit";
        }
        //reset password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        model.addAttribute("user",usersDao.save(user));
        return "redirect:/user/"+id;
    }

    //  DELETE USER
    @PostMapping("/user/{id}/delete")
    public String deleteUser(@PathVariable long id) {
        usersDao.deleteById(id);
        return "redirect:/";
    }

}
