package ru.kata.spring.boot_security.demo.service;

import org.hibernate.HibernateException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.kata.spring.boot_security.demo.dao.RoleDaoImpl;
import ru.kata.spring.boot_security.demo.dao.UserDaoImpl;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
//import ru.kata.spring.boot_security.demo.repo.RoleRepository;
//import ru.kata.spring.boot_security.demo.repo.UserRepository;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

//    private final UserRepository ur;
//    private final RoleRepository rr;
//
//    @Lazy
//    public UserServiceImpl(UserRepository ur,
//                           RoleRepository rr,
//                           PasswordEncoder passwordEncoder) {
//        this.ur = ur;
//        this.rr = rr;
//        this.passwordEncoder = passwordEncoder;
//    }

    private final UserDaoImpl ur;
    private final RoleDaoImpl rr;
    private final PasswordEncoder passwordEncoder;

    @Lazy
    public UserServiceImpl(UserDaoImpl ur,
                           RoleDaoImpl rr,
                           PasswordEncoder passwordEncoder) {
        this.ur = ur;
        this.rr = rr;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = ur.findByUsername(username);
        if (user == null) {
            System.out.println("User '" + username + "' not found");
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return ur.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        Optional<User> userFromDb = Optional.ofNullable(ur.findById(id));
        return userFromDb.orElse(new User());
    }

    @Override
    public Iterable<User> findAll() {
        return ur.findAll();
    }

    @Override
    @Transactional(rollbackOn = HibernateException.class)
    public String save(User user) {

        if (ur.findByUsername(user.getUsername()) != null) {
            return "This username is already in use!";
        }
        if (!checkPassErrors(user).equals("ok")) {
            return checkPassErrors(user);
        }
        if (user.getUsername().length() < 3) {
            return "The username must contain more than 3 characters!";
        }

        Set<Role> roles = new HashSet<>();
        roles.add(rr.findByName("ROLE_USER"));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfPass(user.getPassword());
        ur.save(user);

        return "ok";
    }

    @Override
    @Transactional(rollbackOn = HibernateException.class)
    public String update(User user, String roleAdmin, String pass) {

        if (user.getUsername().length() < 3) {
            return "The username must contain more than 3 characters!";
        }

        Set<Role> roles = new HashSet<>();
        if (roleAdmin != null) {
            roles.add(rr.findByName("ROLE_ADMIN"));
        }
        roles.add(rr.findByName("ROLE_USER"));
        user.setRoles(roles);

        if (pass.equals("")) {
            user.setPassword(findByUsername(user.getUsername()).getPassword());
        } else {
            if (!checkPassErrors(user).equals("ok")) {
                return checkPassErrors(user);
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        //ur.save(user);
        ur.update(user);

        return "ok";
    }

    private String checkPassErrors(User user) {
        if (user.getPassword().length() < 3) {
            return "The password must contain more than 3 characters!";
        }
        if (!user.getPassword().equals(user.getConfPass())) {
            return "Passwords must match!";
        }
        return "ok";
    }

    @Override
    @Transactional(rollbackOn = HibernateException.class)
    public void deleteById(Long id) {
        if (ur.findById(id) != null) {
            ur.deleteById(id);
        }
    }
}
