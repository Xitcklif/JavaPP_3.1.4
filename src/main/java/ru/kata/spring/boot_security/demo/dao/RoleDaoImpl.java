package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager em;

    public RoleDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Role findByName(String name) {
        return findAll().stream()
                .filter(role -> role.getName().equals(name))
                .findAny()
                .orElse(null);
    }

    @Override
    public void save(Role role) {
        em.persist(role);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> findAll() {
        return em.createQuery("select r from Role r", Role.class)
                .getResultList();
    }
}
