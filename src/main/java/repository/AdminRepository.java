package repository;

import model.Admin;
import model.CM;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Dragos on 5/8/2017.
 */
public class AdminRepository implements CRUDRepository {
    private SessionFactory factory;
    public AdminRepository(SessionFactory factory)
    {
        this.factory = factory;
    }
    public Admin findOne(String username) {
        Admin admin = null;
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Admin where username = :username");
            query.setParameter("username", username);
            List<Admin> adminList = query.list();
            if (!adminList.isEmpty())
                admin = adminList.get(0);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return admin;
    }
    public void save(Admin admin) {
        Transaction tx = null;
        Session ses = factory.openSession();
        try {
            tx = ses.beginTransaction();
            Query query = ses.createNativeQuery("INSERT INTO Admin (username, password) VALUES (:username, :password)");
            query.setParameter("username", admin.getUsername());
            query.setParameter("password", admin.getPassword());
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        } finally {
            ses.close();
        }
    }
    public List<Admin> getAll() {
        List<Admin> lusers = null;
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Admin");
            lusers = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return lusers;
    }
    public void delete(String username) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("delete Admin where username = :username");
            query.setParameter("username", username);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
