package repository;

import model.Conference;
import org.hibernate.SessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by stefanvacareanu on 13/05/2017.
 */
public class ConfRepository {
    private static SessionFactory factory;
    private List<Conference> conferences = new ArrayList<Conference>();


    public ConfRepository()
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
            tx = session.beginTransaction();
            conferences = session.createQuery("FROM Conference").list();
            tx.commit();
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

    public List<Conference> getAll(){
        return conferences;
    }

    public Integer addConference(int noPart, String name, String deadlineprop, String deadlineabs){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer ConfID = null;
        try{
            tx = session.beginTransaction();
            Conference c = new Conference(name,noPart, deadlineprop, deadlineabs);
            ConfID = (Integer) session.save(c);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return ConfID;
    }

    public void updateConference(Integer ConfID, int noPart, String name, String deadlineprop, String deadlineabs ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Conference c =
                    (Conference)session.get(Conference.class, ConfID);
            c.setNoParticipants(noPart);
            c.setName(name);
            //c.setDeadline(deadline);
            session.update(c);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to DELETE an employee from the records */
    public void deleteConference(Integer ConfID){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Conference c =
                    (Conference)session.get(Conference.class, ConfID);
            session.delete(c);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}
