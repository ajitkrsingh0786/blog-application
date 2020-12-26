package com.blog.blogapplication.controller;

import com.blog.blogapplication.model.User;
import com.blog.blogapplication.service.declaration.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/showRegistrationForm")
    public String showRegistrationForm(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "html/registrationForm";
    }

    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute("user") User user){
           userService.registerUser(user);
        return  "redirect:/";
    }
}
