package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class IndexController {

    private UserServiceImpl userService;

    public LogoutController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping()
<<<<<<< HEAD:src/main/java/ru/kata/spring/boot_security/demo/controller/LogoutController.java
    public String showUser(HttpServletRequest request,
                           HttpServletResponse response) {
        userService.logoutUser(request, response);
        return "redirect:/";
=======
    public String loginPage() {
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
>>>>>>> eadcb3ee1b800759be262ad4ac23ec99f211ba63:src/main/java/ru/kata/spring/boot_security/demo/controller/IndexController.java
    }
}