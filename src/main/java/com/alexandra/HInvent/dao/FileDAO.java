package com.alexandra.HInvent.dao;

import com.alexandra.HInvent.entities.File;
import com.alexandra.HInvent.entities.Item;
import com.alexandra.HInvent.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class FileDAO {
    public File findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        File file = session.get(File.class, id);
        session.close();
        return file;
    }

    public void save(File file) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(file);
        tx1.commit();
        session.close();
    }

    public void update(File file) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(file);
        tx1.commit();
        session.close();
    }

    public void delete(File file) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(file);
        tx1.commit();
        session.close();
    }

    public List<File> findByItem(Item item) {
        int id = item.getId();
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("FROM File F WHERE F.item.id = " + Integer.toString(id) + " ORDER BY F.id ASC");
        List<File> files = query.list();
        session.close();
        return files;
    }

    public List<File> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<File> cabinets = (List<File>)  session.createQuery("From File").list();
        session.close();
        return cabinets;
    }
}
