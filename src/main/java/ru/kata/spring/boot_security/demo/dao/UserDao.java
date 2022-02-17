package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

public interface UserDao {
    User getUserByUsername(String username);

    User getUserById(Long id);

    Iterable<User> getAllUsers();

    void save(User user);

    void update(User user);

    void deleteById(Long id);
}
