package ru.kata.spring.boot_security.demo.service;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDaoImpl;
import ru.kata.spring.boot_security.demo.model.Role;
//import ru.kata.spring.boot_security.demo.repo.RoleRepository;

import javax.transaction.Transactional;

@Service
public class RoleServiceImpl implements RoleService{

//    private final RoleRepository rr;
//
//    public RoleServiceImpl(RoleRepository rr) {
//        this.rr = rr;
//    }

    private final RoleDaoImpl rr;

    public RoleServiceImpl(RoleDaoImpl rr) {
        this.rr = rr;
    }

    @Override
    public Role getRoleByName(String name) {
        return rr.findByName(name);
    }

    @Override
    @Transactional(rollbackOn = HibernateException.class)
    public void addRoleToTable(Role role) {
        rr.save(role);
    }

    @Override
    public Iterable<Role> getAllRoles() {
        return rr.findAll();
    }
}
