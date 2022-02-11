package ru.kata.spring.boot_security.demo.dao;

//import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{

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

//    private final SessionFactory sessionFactory;
//
//    public RoleDaoImpl(EntityManager em) {
//        this.em = em;
//    }
//
//    @Override
//    public Role findByName(String name) {
//        return (Role) sessionFactory
//                .getCurrentSession()
//                .createQuery("from Role where name = :name")
//                .setParameter("name", name)
//                .uniqueResult();
//    }
//
//    @Override
//    public void save(Role role) {
//        sessionFactory.getCurrentSession().save(role);
//    }
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public Iterable<Role> findAll() {
//        return sessionFactory
//                .getCurrentSession()
//                .createQuery("from Role")
//                .getResultList();
//    }
}
