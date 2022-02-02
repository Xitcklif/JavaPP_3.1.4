package ru.kata.spring.boot_security.demo.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;

@Component
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
