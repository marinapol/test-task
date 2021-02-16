package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.Client;
import com.haulmont.testtask.utils.SessionUtil;
import org.hibernate.Session;

import javax.persistence.PersistenceException;

public class ClientDAO {

    public void saveOrUpdateClient(Client client) {
        Session session = SessionUtil.sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(client);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteClient(Client client) throws PersistenceException {
        Session session = SessionUtil.sessionFactory.openSession();
        session.beginTransaction();
        session.delete(client);
        session.getTransaction().commit();
        session.close();
    }

}
