package ru.kata.spring.boot_security.demo.dao;

//import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    public UserDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public User findByUsername(String username) {
        return findAll().stream()
                .filter(user -> user.getUsername().equals(username))
                .findAny()
                .orElse(null);
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }

    @Override
    public void deleteById(Long id) {
        em.remove(findById(id));
    }

//    private final SessionFactory sessionFactory;
//
//    public UserDaoImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Override
//    public void save(User user) {
//        sessionFactory.getCurrentSession().save(user);
//    }
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public Iterable<User> findAll() {
//        return sessionFactory
//                .getCurrentSession()
//                .createQuery("from User")
//                .getResultList();
//    }
//
//    @Override
//    public User findByUsername(String username) {
//        return (User) sessionFactory
//                .getCurrentSession()
//                .createQuery("from User where username = :name")
//                .setParameter("name", username)
//                .uniqueResult();
//    }
//
//    @Override
//    public User findById(Long id) {
//        return (User) sessionFactory
//                .getCurrentSession()
//                .createQuery("from User where id = :id")
//                .setParameter("id", id)
//                .uniqueResult();
//    }
//
//    @Override
//    public void update(User user, String roleAdmin, String pass) {
//        sessionFactory.getCurrentSession().merge(user);
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        sessionFactory.getCurrentSession().delete(findById(id));
//    }
}
