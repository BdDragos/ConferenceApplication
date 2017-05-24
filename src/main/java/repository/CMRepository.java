package repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Cosmin on 4/4/2017.
 */
public class CMRepository implements CRUDRepository
{
    private static SessionFactory factory;

    @SuppressWarnings("unchecked")
    public CMRepository()
    {
        try
        {
            this.factory = new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable ex)
        {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        Transaction tx = null;
        try
        {

        }
        catch (HibernateException e)
        {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }
        finally
        {
            session.close();
        }
    }

    public boolean login(String username, String password)
    {
        return true;
    }
    public void save(String username, String password, String name, String affiliation, String email, String webpage) {
        Transaction tx = null;
        boolean ret = false;
        Session ses = factory.openSession();
        try{
            tx = ses.beginTransaction();
            Query query = ses.createNativeQuery("INSERT INTO CM (username, password, name, affiliation, email, webpage) VALUES (:username, :password, :name, :affiliation, :email, :webpage)");
            query.setParameter("username", username);
            query.setParameter("password", password);
            query.setParameter("name", name);
            query.setParameter("affiliation", affiliation);
            query.setParameter("email", email);
            query.setParameter("webpage", webpage);
            query.executeUpdate();
            tx.commit();
        }
        catch (HibernateException ex){
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        }
        finally{
            ses.close();
        }
    }
}
