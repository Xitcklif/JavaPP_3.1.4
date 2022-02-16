package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
=======
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
>>>>>>> eadcb3ee1b800759be262ad4ac23ec99f211ba63

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    private final UserServiceImpl userService;
<<<<<<< HEAD

    public AdminPageController(UserServiceImpl userService) {
=======
    private final UserDetailsService userDetailsService;

    public AdminPageController(UserServiceImpl userService,
                               UserDetailsService userDetailsService) {
>>>>>>> eadcb3ee1b800759be262ad4ac23ec99f211ba63
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping()
    public String usersList(Model model, Principal principal) {
        model.addAttribute("username", userDetailsService.loadUserByUsername(principal.getName()));
        model.addAttribute("currUser", userService.findByUsername(principal.getName()));
        model.addAttribute("users", userService.findAll());
        return "admin";
    }

<<<<<<< HEAD
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
=======
    @PostMapping("/delete/{id}")
>>>>>>> eadcb3ee1b800759be262ad4ac23ec99f211ba63
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/new")
<<<<<<< HEAD
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
=======
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

>>>>>>> eadcb3ee1b800759be262ad4ac23ec99f211ba63
        userService.update(user, roleAdmin, pass);
        return "redirect:/admin";
    }
}
