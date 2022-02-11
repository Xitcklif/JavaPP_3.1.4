package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;
    private final UserDetailsService userDetailsService;

    public AdminPageController(UserServiceImpl userService,
                               RoleServiceImpl roleService,
                               UserDetailsService userDetailsService) {
        this.userService = userService;
        this.roleService = roleService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping()
    public String usersList(Model model, Principal principal) {
        model.addAttribute("username", userDetailsService.loadUserByUsername(principal.getName()));
        model.addAttribute("currUser", userService.findByUsername(principal.getName()));
        model.addAttribute("users", userService.findAll());
        System.out.println(userService.findAll());
        return "admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/new")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam(name = "role_admin", required = false) String roleAdmin,
                          Model model) {
//        String msg;
//        if (!(msg = userService.save(user, roleAdmin)).equals("ok")) {
//            model.addAttribute("errorText", msg);
//            return "create";
//        }

        userService.save(user, roleAdmin);
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam(name = "role_admin", required = false) String roleAdmin,
                           @RequestParam(name = "password", required = false) String pass,
                           @ModelAttribute("user") User user,
                           Model model) {

//        String msg;
//        if (!(msg = userService.update(user, roleAdmin, pass)).equals("ok")) {
//            model.addAttribute("errorText", msg);
//            return "/edit";
//        }

        userService.update(user, roleAdmin, pass);
        return "redirect:/admin";
    }
}
