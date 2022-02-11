package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;
    private final UserDetailsService userDetailsService;

    public UserController(UserServiceImpl userService, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping()
    public String showUser(Model model, Principal principal) {
        UserDetails user = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userService.findByUsername(user.getUsername()));
        return "user";
    }
}