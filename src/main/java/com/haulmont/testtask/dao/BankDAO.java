package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.Bank;
import com.haulmont.testtask.utils.SessionUtil;
import org.hibernate.Session;
import java.util.List;

public class BankDAO {

    public List<Bank> getAllBanks() {
        Session session = SessionUtil.sessionFactory.openSession();
        session.beginTransaction();
        List<Bank> banks = session.createQuery("select b from Bank b", Bank.class).getResultList();
        session.getTransaction().commit();
        session.close();
        return banks;
    }

    public void saveOrUpdateBank(Bank bank) {
        Session session = SessionUtil.sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(bank);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteBank(Bank bank) {
        Session session = SessionUtil.sessionFactory.openSession();
        session.beginTransaction();
        session.delete(bank);
        session.getTransaction().commit();
        session.close();
    }

}
