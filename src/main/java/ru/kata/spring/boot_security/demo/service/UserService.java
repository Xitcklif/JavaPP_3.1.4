package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

public interface UserService {

    User findByUsername(String username);
    User findById(Long id);
    Iterable<User> findAll();
    void save(User user);
    void update(User user, String roleAdmin, String pass);
    void deleteById(Long id);

}
