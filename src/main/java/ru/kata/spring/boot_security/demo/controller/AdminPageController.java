package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    private final UserServiceImpl userService;

    public AdminPageController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String usersList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin";
    }

    @GetMapping("/new")
    public String newUserGet(Model model) {
        model.addAttribute("user", new User());
        return "/create";
    }

    @GetMapping("/edit/{id}")
    public String editUserGet(@PathVariable("id") long id,
                              Model model) {
        model.addAttribute("user", userService.findById(id));
        return "/edit";
    }

    @PostMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/new")
    public String newUserPost(@ModelAttribute("user") User user,
                              Model model) {
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/edit/{id}")
    public String editUserPost(@RequestParam(name = "role_admin", required = false) String roleAdmin,
                               @RequestParam(name = "password", required = false) String pass,
                               @ModelAttribute("user") User user,
                               Model model) {
        userService.update(user, roleAdmin, pass);
        return "redirect:/admin";
    }
}
