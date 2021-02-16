package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.Credit;
import com.haulmont.testtask.utils.SessionUtil;
import org.hibernate.Session;

public class CreditDAO {

    public void saveOrUpdateCredit(Credit credit) {
        Session session = SessionUtil.sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(credit);
        session.getTransaction().commit();
        session.close();
    }
    public void deleteCredit(Credit credit) {
        Session session = SessionUtil.sessionFactory.openSession();
        session.beginTransaction();
        session.delete(credit);
        session.getTransaction().commit();
        session.close();
    }




}
