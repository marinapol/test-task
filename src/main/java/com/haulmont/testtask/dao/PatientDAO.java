package com.haulmont.testtask.dao;

import com.haulmont.testtask.utils.SessionUtil;
import org.hibernate.Session;

import java.util.List;

public class PatientDAO {
/*

    public List<Patient> getAllPatients(){
        Session session = SessionUtil.getSession();
        session.beginTransaction();
        List<Patient> patients = session.createQuery("from Patient").getResultList();
        session.getTransaction().commit();
        SessionUtil.closeSession();
        return patients;
    }

    public void addPatient(String name, String surname, String patronymic, String phone) {
        Session session = SessionUtil.getSession();
        session.beginTransaction();
        Patient patient = new Patient(name, surname, patronymic, phone);
        session.save(patient);
        session.getTransaction().commit();
        SessionUtil.closeSession();
    }

    public void editPatient(Patient patient) {

        Session session = SessionUtil.getSession();
        session.beginTransaction();
        session.save(patient);//??????????????????????
        session.getTransaction().commit();
        SessionUtil.closeSession();

    }*/


}
