package com.ddl.controller;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // 检查文件是否存在
            if (HibernateUtil.class.getResource("/com/ddl/resources/hibernate.cfg.xml") == null) {
                throw new RuntimeException("hibernate.cfg.xml not found");
            }
            return new Configuration().configure("/com/ddl/resources/hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}