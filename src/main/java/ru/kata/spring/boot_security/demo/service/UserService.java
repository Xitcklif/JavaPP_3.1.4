package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

public interface UserService {

    User findByUsername(String username);
    User findById(Long id);
    Iterable<User> findAll();
    boolean save(User user);
    boolean saveAsAdmin(User user);
    boolean deleteById(Long id);

}