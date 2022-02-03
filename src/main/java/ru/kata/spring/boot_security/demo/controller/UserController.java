package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.repo.UserRepository;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserDetailsService userDetailsService;

    public UserController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping()
    public String showUser(Model model, Principal principal) {
        UserDetails user = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }
}