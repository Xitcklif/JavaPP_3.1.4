package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        if (user.getPassword().length() < 3) {
            model.addAttribute("passwordErr", "passwords should more 3 symbols");
            return "regist";
        }
        if (user.getUsername().length() < 3) {
            model.addAttribute("usernameErr", "username should more 3 symbols");
            return "regist";
        }
        if (!user.getPassword().equals(user.getConfPass())) {
            model.addAttribute("passwordErr", "passwords should be equals");
            return "regist";
        }
        if (!userService.save(user)) {
            model.addAttribute("usernameErr", "This username is already used");
            return "regist";
        }
        return "redirect:/";
    }
}
