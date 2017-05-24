package repository;

import model.DefaultUser;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by Cosmin on 5/22/2017.
 */
public class DefaultUserRepository {
    private SessionFactory factory;
    public DefaultUserRepository(SessionFactory factory)
    {
        this.factory = factory;
    }
    public List<DefaultUser> getAll() {
        List<DefaultUser> lusers = null;
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM DefaultUser");
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
    public DefaultUser findOneByUsername(String username) {
        DefaultUser du = null;
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM DefaultUser where username = :username");
            query.setParameter("username", username);
            List<DefaultUser> lusers = query.list();
            if (!lusers.isEmpty())
                du = lusers.get(0);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return du;
    }
    public DefaultUser findOneByPassword(String password) {
        DefaultUser du = null;
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM DefaultUser where password = :password");
            query.setParameter("password", password);
            List<DefaultUser> lusers = query.list();
            if (!lusers.isEmpty())
                du = lusers.get(0);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return du;
    }
    public void delete(String username) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("delete DefaultUser where username = :username");
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
    public void add(String username, String password) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createNativeQuery("INSERT INTO userdefault (username, password) VALUES (\'"+username+"\', \'"+password+"\')");
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
