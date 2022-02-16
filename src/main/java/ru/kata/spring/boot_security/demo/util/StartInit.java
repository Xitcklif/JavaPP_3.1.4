package ru.kata.spring.boot_security.demo.util;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

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
<<<<<<< HEAD
        User root = new User("root", "root", "root");

=======
>>>>>>> eadcb3ee1b800759be262ad4ac23ec99f211ba63
        Role user = new Role("ROLE_USER");
        roleService.addRoleToTable(user);

        Role admin = new Role("ROLE_ADMIN");
        roleService.addRoleToTable(admin);

<<<<<<< HEAD
        userService.save(root);
        userService.update(root, "admin", "");
=======
        User adm = new User("adm", "adm", "adm");
        userService.save(adm, "admin");

        User root = new User("root", "root", "root");
        userService.save(root, "admin");
>>>>>>> eadcb3ee1b800759be262ad4ac23ec99f211ba63
    }
}
