package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/regist")
public class RegistController {

    private final UserService userService;

    public RegistController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String registPage(Model model) {
        model.addAttribute("user", new User());
        return ("regist");
    }

    @PostMapping()
    public String registNewUser(@ModelAttribute("user") User user,
                                Model model) {
        userService.save(user);
        return "login";
    }
}
