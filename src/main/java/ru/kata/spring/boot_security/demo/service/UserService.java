package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repo.*;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository ur;
    private final RoleRepository rr;

    public UserService(UserRepository ur, RoleRepository rr) {
        this.ur = ur;
        this.rr = rr;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = ur.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User findById(Long id) {
        Optional<User> userFromDb = ur.findById(id);

        return userFromDb.orElse(new User());
    }

    public Iterable<User> findAll() {
        return ur.findAll();
    }

    public boolean save(User user) {

        if (ur.findByUsername(user.getUsername()) != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role("ROLE_USER")));
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        ur.save(user);

        return true;
    }

    public void setAdminRole(User user) {
        user.getRoles().add(new Role("ROLE_ADMIN"));
    }

    public void addRoleToTable(Role role) {
        rr.save(role);
    }

    public boolean deleteById(Long id) {
        if (ur.findById(id).isPresent()) {
            ur.deleteById(id);
            return true;
        }

        return false;
    }
}
