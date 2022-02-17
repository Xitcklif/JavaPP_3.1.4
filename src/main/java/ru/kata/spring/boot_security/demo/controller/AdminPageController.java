package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    private final UserServiceImpl userService;
    private final UserDetailsService userDetailsService;

    public AdminPageController(UserServiceImpl userService,
                               UserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping()
    public String usersList(Model model, Principal principal) {
        model.addAttribute("username", userDetailsService.loadUserByUsername(principal.getName()));
        model.addAttribute("currUser", userService.getUserByUsername(principal.getName()));
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/new")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam(name = "roleAdmin", required = false) String roleAdmin) {
        userService.save(user, roleAdmin);
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam(name = "roleAdmin", required = false) String roleAdmin,
                           @RequestParam(name = "password", required = false) String pass,
                           @ModelAttribute("user") User user) {
        userService.update(user, roleAdmin, pass);
        return "redirect:/admin";
    }
}
