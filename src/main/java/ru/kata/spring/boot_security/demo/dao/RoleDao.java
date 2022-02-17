package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

public interface RoleDao {
    Role getRoleByName(String name);

    void save(Role role);

    Iterable<Role> getAllRoles();
}
