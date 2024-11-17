package com.ddl.controller;

import com.ddl.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Optional;

public class UserRepository {
    private SessionFactory sessionFactory;

    public UserRepository() {
        sessionFactory = new Configuration().configure("com/ddl/resources/hibernate.cfg.xml").buildSessionFactory();
    }

    public void save(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    public Optional<User> findByUsername(String username) {
        Session session = sessionFactory.openSession();
        User user = session.createQuery("FROM User WHERE username = :username", User.class)
                .setParameter("username", username)
                .uniqueResult();
        session.close();
        return Optional.ofNullable(user);
    }
}