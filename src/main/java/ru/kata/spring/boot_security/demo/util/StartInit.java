package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class StartInit {

    private final UserService userService;

    public StartInit(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    private void createRoot() {
        User root = new User("root", "root", "root");
//        Role user = new Role("ROLE_USER");
//        Role admin = new Role("ROLE_ADMIN");
//
//        userService.addRoleToTable(user);
//        userService.addRoleToTable(admin);

        userService.save(root);
//        userService.setAdminRole(root);
    }
}
