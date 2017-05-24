package repository;

import model.Conference;
import model.Sections;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by stefanvacareanu on 13/05/2017.
 */
public class SectionRepository {
    private static SessionFactory factory;
    private List<Sections> sections = new ArrayList<Sections>();


    public SectionRepository()
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
            sections = session.createQuery("FROM Sections ").list();
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

    public List<Sections> getAll(){
        return sections;
    }

    public List<Sections> getAllSectionByConfId(int confid){
        List<Sections> s = new ArrayList<Sections>();
        for (Sections a : sections){
            if (a.getIdConference() == confid){
                s.add(a);
            }
        }
        return s;
    }

    public Integer addSection(int idConf,int sesC, String name,String h,String d){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer SectionID = null;
        try{
            tx = session.beginTransaction();
            Sections s = new Sections(idConf,sesC,name,h,d);
            SectionID = (Integer) session.save(s);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return SectionID;
    }

    public void updateSection(Integer SectionID, int sesC, String name,String h,String d ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Sections s =
                    (Sections)session.get(Sections.class, SectionID);
            s.setName(name);
            s.setSesChair(sesC);
            s.setDate(d);
            s.setHour(h);
            session.update(s);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to DELETE an employee from the records */
    public void deleteSection(Integer SectionID){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Sections s =
                    (Sections)session.get(Sections.class, SectionID);
            session.delete(s);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}
