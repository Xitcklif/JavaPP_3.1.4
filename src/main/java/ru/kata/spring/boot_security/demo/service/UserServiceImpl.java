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

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

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
    public void save(User user, String adm) {

        if (ur.findByUsername(user.getUsername()) != null ||
                user.getUsername().length() < 1 ||
                checkPassErrors(user)) {
            return;
        }

        Set<Role> roles = new HashSet<>();
        if (adm != null) {
            roles.add(rr.findByName("ROLE_ADMIN"));
        }
        roles.add(rr.findByName("ROLE_USER"));
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        ur.save(user);
    }

    @Override
    @Transactional(rollbackOn = HibernateException.class)
    public void update(User user, String roleAdmin, String pass) {

        if (user.getUsername().length() < 1) {
            return;
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
            if (checkPassErrors(user)) {
                return;
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        ur.update(user);
    }

    private boolean checkPassErrors(User user) {
        return (user.getPassword().length() < 1 ||
                !user.getPassword().equals(user.getConfPass()));
    }

    @Override
    @Transactional(rollbackOn = HibernateException.class)
    public void deleteById(Long id) {
        if (ur.findById(id) != null) {
            ur.deleteById(id);
        }
    }
}
