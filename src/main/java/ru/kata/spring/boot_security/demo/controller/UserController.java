package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.repo.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String showUser(Model model) {
        String username = (String) auth.getPrincipal();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "user";
    }
}