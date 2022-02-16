package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    private UserServiceImpl userService;

    public LogoutController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showUser(HttpServletRequest request,
                           HttpServletResponse response) {
        userService.logoutUser(request, response);
        return "redirect:/";
    }
}