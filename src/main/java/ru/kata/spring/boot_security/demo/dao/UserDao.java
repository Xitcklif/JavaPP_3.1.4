package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

public interface UserDao {
    User findByUsername(String username);
    User findById(Long id);
    Iterable<User> findAll();
    void save(User user);
    void update(User user);
    void deleteById(Long id);
}
