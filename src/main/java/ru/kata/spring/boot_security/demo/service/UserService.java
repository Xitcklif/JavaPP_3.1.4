package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

public interface UserService {
    User getUserByUsername(String username);
    Iterable<User> getAllUsers();
    void save(User user);
    void save(User user, boolean adm);
    void update(User user);
    void deleteById(long id);
    User getUserById(long id);
}
