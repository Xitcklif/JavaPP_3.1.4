package ru.kata.spring.boot_security.demo.util;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.*;
import ru.kata.spring.boot_security.demo.service.*;

import javax.annotation.PostConstruct;

@Component
public class StartInit {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    public StartInit(UserServiceImpl userService,
                     RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void createRoot() {
        Role userRole = new Role("ROLE_USER");
        roleService.addRoleToTable(userRole);

        Role adminRole = new Role("ROLE_ADMIN");
        roleService.addRoleToTable(adminRole);

        Role viewerRole = new Role("ROLE_VIEWER");
        roleService.addRoleToTable(viewerRole);

        Role readerRole = new Role("ROLE_READER");
        roleService.addRoleToTable(readerRole);

        User root = new User("root", "root", "root", "ROLE_USER ROLE_ADMIN");
        userService.save(root);

        User user = new User("user", "user", "user");
        userService.save(user);
    }
}
