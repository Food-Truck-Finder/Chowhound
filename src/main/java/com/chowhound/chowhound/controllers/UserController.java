package com.chowhound.chowhound.controllers;

import com.chowhound.chowhound.models.User;
import com.chowhound.chowhound.repos.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserRepo users;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepo users, PasswordEncoder passwordEncoder){
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/register")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "users/register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user, Model model){

        String redirectStr = "redirect:/login", errorMsg = "";

        try{
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        users.save(user);
        model.addAttribute("user", user);

        } catch (Exception e){
            model.addAttribute("error", "Duplicate username found");
            redirectStr = "redirect:/register?error";
        }
        return redirectStr;
    }

    //mapping to get user ifo update view
    @GetMapping("/update_profile")
    public String showUpdateProfileForm(Model model){
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "users/update";
    }

     // mapping for user to update email and/or password
    @PostMapping("/update_profile")
    public String saveProfileUpdate(@ModelAttribute User user, Model model){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        User userToUpdate = users.findById(user.getId());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setEmail(user.getEmail());

        users.save(userToUpdate);
        model.addAttribute("user", userToUpdate);
        return "redirect:/index";
    }
}
