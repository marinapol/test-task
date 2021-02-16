package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.Bank;
import com.haulmont.testtask.entity.CreditOffer;
import com.haulmont.testtask.entity.Payment;
import com.haulmont.testtask.utils.SessionUtil;
import org.hibernate.Session;
import javax.persistence.Query;
import java.util.List;

public class CreditOfferDAO {

    public void saveOrUpdateCreditOffer(CreditOffer creditOffer) {
        Session session = SessionUtil.sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(creditOffer);
        session.getTransaction().commit();
        session.close();

    }
    public void deleteCreditOffer(CreditOffer creditOffer) {
        Session session = SessionUtil.sessionFactory.openSession();
        session.beginTransaction();
        session.delete(creditOffer);
        session.getTransaction().commit();
        session.close();
    }

    public List<CreditOffer> getAllCreditOffers(Bank bank) {
        Session session = SessionUtil.sessionFactory.openSession();
        session.beginTransaction();
        List<CreditOffer> offers;
        Query query = session.createQuery("from CreditOffer c " +
                "where c.client.clientBank = :bank", CreditOffer.class);
        query.setParameter("bank", bank);
        offers = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return offers;
    }

    public void deletePaymentSchedule(CreditOffer creditOffer) {
        Session session = SessionUtil.sessionFactory.openSession();
        session.beginTransaction();
        for(Payment payment : creditOffer.getPaymentSchedule()) {
            session.delete(payment);
        }
        session.getTransaction().commit();
        session.close();
    }
}
