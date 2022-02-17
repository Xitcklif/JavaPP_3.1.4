package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        model.addAttribute("user",
                userService.getUserByUsername(
                        userDetailsService.loadUserByUsername(
                                principal.getName()).getUsername()));
        return "user";
    }
}