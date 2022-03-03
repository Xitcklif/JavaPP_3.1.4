package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

public interface UserDao {
    User getUserByUsername(String username);
    Iterable<User> getAllUsers();
    void save(User user);
    void update(User user);
    void deleteById(long id);
    User getUserById(long id);
}
