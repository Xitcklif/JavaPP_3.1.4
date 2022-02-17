package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

public interface UserService {
    User getUserByUsername(String username);

    User getUserById(Long id);

    Iterable<User> getAllUsers();

    void save(User user, String adm);

    void update(User user, String roleAdmin, String pass);

    void deleteById(Long id);
}
