package ru.kata.spring.boot_security.demo.service;

import org.hibernate.HibernateException;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import ru.kata.spring.boot_security.demo.dao.RoleDaoImpl;
import ru.kata.spring.boot_security.demo.dao.UserDaoImpl;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
<<<<<<< HEAD
=======
//import ru.kata.spring.boot_security.demo.repo.RoleRepository;
//import ru.kata.spring.boot_security.demo.repo.UserRepository;
>>>>>>> eadcb3ee1b800759be262ad4ac23ec99f211ba63

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

<<<<<<< HEAD
=======
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

>>>>>>> eadcb3ee1b800759be262ad4ac23ec99f211ba63
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
<<<<<<< HEAD
    public void save(User user) {

        if (ur.findByUsername(user.getUsername()) != null ||
                user.getUsername().length() < 1 ||
                checkPassErrors(user)) {
            return ;
=======
    public String save(User user, String adm) {

        if (ur.findByUsername(user.getUsername()) != null) {
            return "This username is already in use!";
        }
        if (!checkPassErrors(user).equals("ok")) {
            return checkPassErrors(user);
        }
        if (user.getUsername().length() < 3) {
            return "The username must contain more than 3 characters!";
>>>>>>> eadcb3ee1b800759be262ad4ac23ec99f211ba63
        }

        Set<Role> roles = new HashSet<>();
        if (adm != null) {
            roles.add(rr.findByName("ROLE_ADMIN"));
        }
        roles.add(rr.findByName("ROLE_USER"));
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
<<<<<<< HEAD

        ur.save(user);
=======
        user.setConfPass(user.getPassword());

        ur.save(user);

        return "ok";
>>>>>>> eadcb3ee1b800759be262ad4ac23ec99f211ba63
    }

    @Override
    @Transactional(rollbackOn = HibernateException.class)
<<<<<<< HEAD
    public void update(User user, String roleAdmin, String pass) {

        if (user.getUsername().length() < 1) {
            return ;
=======
    public String update(User user, String roleAdmin, String pass) {

        if (user.getUsername().length() < 3) {
            return "The username must contain more than 3 characters!";
>>>>>>> eadcb3ee1b800759be262ad4ac23ec99f211ba63
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
<<<<<<< HEAD
            if (checkPassErrors(user)) {
                return ;
=======
            if (!checkPassErrors(user).equals("ok")) {
                return checkPassErrors(user);
>>>>>>> eadcb3ee1b800759be262ad4ac23ec99f211ba63
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

<<<<<<< HEAD
        ur.update(user);
    }

    private boolean checkPassErrors(User user) {
        return (user.getPassword().length() < 1 ||
                !user.getPassword().equals(user.getConfPass()));
=======
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
>>>>>>> eadcb3ee1b800759be262ad4ac23ec99f211ba63
    }

    @Override
    @Transactional(rollbackOn = HibernateException.class)
    public void deleteById(Long id) {
        if (ur.findById(id) != null) {
            ur.deleteById(id);
        }
<<<<<<< HEAD
    }

    public void logoutUser(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
=======
>>>>>>> eadcb3ee1b800759be262ad4ac23ec99f211ba63
    }
}
