package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    private final UserService userService;

    public AdminPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String usersList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin";
    }

    @PostMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
