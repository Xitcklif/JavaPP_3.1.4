package ru.kata.spring.boot_security.demo.service;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
<<<<<<< HEAD

import ru.kata.spring.boot_security.demo.dao.RoleDaoImpl;
import ru.kata.spring.boot_security.demo.model.Role;
=======
import ru.kata.spring.boot_security.demo.dao.RoleDaoImpl;
import ru.kata.spring.boot_security.demo.model.Role;
//import ru.kata.spring.boot_security.demo.repo.RoleRepository;
>>>>>>> eadcb3ee1b800759be262ad4ac23ec99f211ba63

import javax.transaction.Transactional;

@Service
public class RoleServiceImpl implements RoleService{

<<<<<<< HEAD
    private final RoleDaoImpl rr;

=======
//    private final RoleRepository rr;
//
//    public RoleServiceImpl(RoleRepository rr) {
//        this.rr = rr;
//    }

    private final RoleDaoImpl rr;

>>>>>>> eadcb3ee1b800759be262ad4ac23ec99f211ba63
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
