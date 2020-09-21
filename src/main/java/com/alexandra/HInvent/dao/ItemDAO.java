package com.alexandra.HInvent.dao;

import com.alexandra.HInvent.entities.Cabinet;
import com.alexandra.HInvent.entities.Item;
import com.alexandra.HInvent.entities.User;
import com.alexandra.HInvent.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ItemDAO {
    public Item findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Item item = session.get(Item.class, id);
        session.close();
        return item;
    }

    public List<Item> findByUser(User user) {
        int id = user.getId();
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("FROM Item I WHERE I.user.id = " + Integer.toString(id) + " ORDER BY I.id ASC");
        List<Item> items = query.list();
        session.close();
        return items;
    }

    public List<Item> findByUserDebt(User user) {
        int id = user.getId();
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("FROM Item I WHERE I.status = 'Недостача' and I.user.id = " + Integer.toString(id) + " ORDER BY I.id ASC");
        List<Item> items = query.list();
        session.close();
        return items;
    }

    public List<Item> findByCabinet(Cabinet cabinet) {
        int id = cabinet.getId();
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("FROM Item I WHERE I.cabinet.id = " + Integer.toString(id) + " ORDER BY I.id ASC");
        List<Item> items = query.list();
        session.close();
        return items;
    }

    public void save(Item item) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(item);
        tx1.commit();
        session.close();
    }

    public void update(Item item) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(item);
        tx1.commit();
        session.close();
    }

    public void delete(Item item) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(item);
        tx1.commit();
        session.close();
    }

    public List<Item> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Item> items = (List<Item>)  session.createQuery("From Item").list();
        session.close();
        return items;
    }
}
