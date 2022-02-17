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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDaoImpl userDao;
    private final RoleDaoImpl roleDao;
    private final PasswordEncoder passwordEncoder;

    @Lazy
    public UserServiceImpl(UserDaoImpl userDao,
                           RoleDaoImpl roleDao,
                           PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            System.out.println("User '" + username + "' not found");
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userFromDb = Optional.ofNullable(userDao.getUserById(id));
        return userFromDb.orElse(new User());
    }

    @Override
    public Iterable<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional(rollbackOn = HibernateException.class)
    public void save(User user) {

        if (userDao.getUserByUsername(user.getUsername()) != null ||
                user.getUsername().length() < 1 ||
                getPassErrors(user)) {
            return;
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getRoleByName("ROLE_USER"));
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfPass(user.getPassword());

        userDao.save(user);
    }

    @Override
    @Transactional(rollbackOn = HibernateException.class)
    public void update(User user, String roleAdmin, String pass) {

        if (user.getUsername().length() < 1) {
            return;
        }

        Set<Role> roles = new HashSet<>();
        if (roleAdmin != null) {
            roles.add(roleDao.getRoleByName("ROLE_ADMIN"));
        }
        roles.add(roleDao.getRoleByName("ROLE_USER"));
        user.setRoles(roles);

        if (pass.equals("")) {
            user.setPassword(getUserByUsername(user.getUsername()).getPassword());
        } else {
            if (getPassErrors(user)) {
                return;
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userDao.update(user);
    }

    private boolean getPassErrors(User user) {
        return (user.getPassword().length() < 1 ||
                !user.getPassword().equals(user.getConfPass()));
    }

    @Override
    @Transactional(rollbackOn = HibernateException.class)
    public void deleteById(Long id) {
        if (userDao.getUserById(id) != null) {
            userDao.deleteById(id);
        }
    }

    public void logoutUser(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }
}
