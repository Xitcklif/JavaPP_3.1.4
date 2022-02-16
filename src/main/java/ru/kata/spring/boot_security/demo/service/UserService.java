package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

public interface UserService {

    User findByUsername(String username);
    User findById(Long id);
    Iterable<User> findAll();
<<<<<<< HEAD
    void save(User user);
    void update(User user, String roleAdmin, String pass);
=======
    String save(User user, String adm);
    String update(User user, String roleAdmin, String pass);
>>>>>>> eadcb3ee1b800759be262ad4ac23ec99f211ba63
    void deleteById(Long id);

}
