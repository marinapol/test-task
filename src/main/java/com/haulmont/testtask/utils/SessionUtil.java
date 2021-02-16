package com.haulmont.testtask.utils;

import com.haulmont.testtask.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionUtil {
    public static SessionFactory sessionFactory;

    static {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").
                    addAnnotatedClass(Bank.class).
                    addAnnotatedClass(Client.class).
                    addAnnotatedClass(Credit.class).
                    addAnnotatedClass(CreditOffer.class).
                    addAnnotatedClass(Payment.class).
                    buildSessionFactory();
    }

}
