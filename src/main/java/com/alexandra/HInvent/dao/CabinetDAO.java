package com.alexandra.HInvent.dao;

import com.alexandra.HInvent.entities.Cabinet;
import com.alexandra.HInvent.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CabinetDAO {
    public Cabinet findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Cabinet cabinet = session.get(Cabinet.class, id);
        session.close();
        return cabinet;
    }

    public void save(Cabinet cabinet) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(cabinet);
        tx1.commit();
        session.close();
    }

    public void update(Cabinet cabinet) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(cabinet);
        tx1.commit();
        session.close();
    }

    public void delete(Cabinet cabinet) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(cabinet);
        tx1.commit();
        session.close();
    }

    public List<Cabinet> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Cabinet> cabinets = (List<Cabinet>)  session.createQuery("From Cabinet").list();
        session.close();
        return cabinets;
    }
}
