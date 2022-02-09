package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;
    private final PasswordEncoder passwordEncoder;

    public AdminPageController(UserServiceImpl userService,
                               RoleServiceImpl roleService,
                               PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
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

    @GetMapping("/new")
    public String newUserGet(Model model) {
        model.addAttribute("user", new User());
        return "/create";
    }

    @PostMapping("/new")
    public String newUserPost(@ModelAttribute("user") User user,
                              Model model) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getPassword().length() < 1) {
            model.addAttribute("passwordErr", "passwords should more 3 symbols");
            return "create";
        }
        if (user.getUsername().length() < 1) {
            model.addAttribute("usernameErr", "username should more 3 symbols");
            return "create";
        }
        if (!userService.save(user)) {
            model.addAttribute("usernameErr", "This username is already used");
            return "create";
        }
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUserGet(@PathVariable("id") long id,
                              Model model) {
        model.addAttribute("user", userService.findById(id));
        return "/edit";
    }

    @PostMapping("/edit/{id}")
    public String editUserPost(@PathVariable("id") long id,
                               @RequestParam(name = "role_admin", required = false) String roleAdmin,
                               @RequestParam(name = "password", required = false) String pass,
                               @ModelAttribute("user") User user,
                               Model model) {

        Set<Role> roles = new HashSet<>();
        if (roleAdmin != null) {
            roles.add(roleService.getRoleByName("ROLE_ADMIN"));
        }
        roles.add(roleService.getRoleByName("ROLE_USER"));
        user.setRoles(roles);

        if (pass.equals("")) {
            user.setPassword(userService.findByUsername(user.getUsername()).getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (user.getPassword().length() < 1) {
            model.addAttribute("passwordErr", "passwords should more 3 symbols");
            return "/edit" + id;
        }
        if (user.getUsername().length() < 1) {
            model.addAttribute("usernameErr", "username should more 3 symbols");
            return "/edit" + id;
        }

        userService.update(user);
        return "redirect:/admin";
    }
}
