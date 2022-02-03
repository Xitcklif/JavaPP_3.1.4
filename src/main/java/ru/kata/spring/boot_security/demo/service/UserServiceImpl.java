package ru.kata.spring.boot_security.demo.service;

import org.hibernate.HibernateException;
import org.hibernate.service.NullServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repo.RoleRepository;
import ru.kata.spring.boot_security.demo.repo.UserRepository;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository ur;
    private final RoleRepository rr;
    private final PasswordEncoder passwordEncoder;

    @Lazy
    public UserServiceImpl(UserRepository ur, PasswordEncoder passwordEncoder, RoleRepository rr) {
        this.ur = ur;
        this.passwordEncoder = passwordEncoder;
        this.rr = rr;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = ur.findByUsername(username);
        if (user == null) {
            System.out.println("User \'" + username + "\' not found");
            throw new UsernameNotFoundException("User \'" + username + "\' not found");
        }
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return ur.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        Optional<User> userFromDb = ur.findById(id);
        return userFromDb.orElse(new User());
    }

    @Override
    public Iterable<User> findAll() {
        return ur.findAll();
    }

    @Override
    @Transactional(rollbackOn = HibernateException.class)
    public boolean save(User user) {
        if (ur.findByUsername(user.getUsername()) != null) {
            return false;
        }
        Set<Role> roles = new HashSet<>();
        roles.add(rr.findByName("ROLE_USER"));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        ur.save(user);

        return true;
    }

    @Override
    @Transactional(rollbackOn = HibernateException.class)
    public boolean saveAsAdmin(User user) {
        if (ur.findByUsername(user.getUsername()) != null) {
            return false;
        }
        Set<Role> roles = new HashSet<>();
        roles.add(rr.findByName("ROLE_USER"));
        roles.add(rr.findByName("ROLE_ADMIN"));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        ur.save(user);

        return true;
    }

    @Override
    @Transactional(rollbackOn = HibernateException.class)
    public boolean deleteById(Long id) {
        if (ur.findById(id).isPresent()) {
            ur.deleteById(id);
            return true;
        }
        return false;
    }
}
