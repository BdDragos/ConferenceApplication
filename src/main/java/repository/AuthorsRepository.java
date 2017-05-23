package repository;

import model.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.*;
import org.hibernate.query.Query;

import java.math.BigInteger;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Cosmin on 4/6/2017.
 */
public class AuthorsRepository implements CRUDRepository
{
    private static SessionFactory factory;
    private Statement statement;
    private Connection connection;
    private int idforfile = 0;
    @SuppressWarnings("unchecked")
    public AuthorsRepository()
    {
        String url = "jdbc:mysql://localhost:3306/cms";
        String user = "root";
        String password = "";
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, user, password);

            statement = connection.createStatement();
            statement.execute("USE cms");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

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

    public List<Author> getAllAuthor()
    {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Author> sectList = new ArrayList<Author>();
        try {
            tx = session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("FROM Author");
            sectList = query.list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return sectList;
        }
    }

    public List<File> getAllFiles()
    {
        List<Integer> fls = new ArrayList<Integer>();
        List<File> sectList = new ArrayList<File>();
        try {
            String query = "SELECT * FROM legaf WHERE ida = ?";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, idforfile);
            ResultSet result = preparedStmt.executeQuery();
            while (result.next())
            {
                fls.add(result.getInt("idf"));
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        for (Integer idrez : fls)
        {
            List<File> rzm = new ArrayList<File>();
            Session session = factory.openSession();
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                org.hibernate.query.Query query = session.createQuery("FROM File WHERE idF = :idF");
                query.setParameter("idF", idrez);
                rzm = query.list();
                sectList.add(rzm.get(0));
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();

            }
        }
        return sectList;
    }

    public List<Author> getAfterAuthotNOTid(List<Author> id)
    {
        List<Author> sectList = getAllAuthor();
        return sectList;
    }

    public int uploadFile(String prop,String key,String top,String link, String abs,List<Author> autr)
    {
        int lastId = 0;
        Session session = factory.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            Query query = session.createNativeQuery("INSERT INTO file (keywords, topic, filedoc, abstractData,cmaccept, titlu) VALUES (:keywords, :topic, :filedoc, :abstractData,:borderline , :titlu)");
            query.setParameter("keywords", key);
            query.setParameter("topic", top);
            query.setParameter("filedoc", link);
            query.setParameter("abstractData", abs);
            query.setParameter("borderline", "borderline");
            query.setParameter("titlu", prop);
            query.executeUpdate();
            tx.commit();

            final String sql = "SELECT max( i.idF ) FROM File i";
            lastId = (Integer) session.createQuery( sql ).uniqueResult();

        }
        catch (HibernateException ex){

            if (tx != null) tx.rollback();
            ex.printStackTrace();
            return 0;
        }
            for(Author a : autr)
            {
                try
                {
                    String query = "INSERT INTO legaf(ida, idf) VALUES (?,?)";
                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setInt(1, a.getIda());
                    preparedStmt.setInt(2, lastId);
                    preparedStmt.execute();
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                    return 0;
                }
            }
            session.close();
            return 1;
    }
    public List<Conference> getAllConferences()
    {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Conference> sectList = new ArrayList<Conference>();
        try {
            tx = session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("FROM Conference");
            sectList = query.list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally
        {

            session.close();
            return sectList;
        }
    }

    public List<Sections> findByConfId(int idC)
    {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Sections> sectList = new ArrayList<Sections>();
        try {
            tx = session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("FROM Sections where idConference = :idConference");
            query.setParameter("idConference", idC);
            sectList = query.list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return sectList;
        }
    }

    public List<String> returnDeadline(int idC)
    {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Conference> sectList;
        Conference conf = null;
        List<String> deadlineList = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("FROM Conference where idConference = :idConference");
            query.setParameter("idConference", idC);
            sectList = query.list();

            conf = sectList.get(0);;
            if (conf!= null)
            {
                deadlineList.add(conf.getDeadlineProposal());
                deadlineList.add(conf.getDeadlineAbstract());
            }

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return deadlineList;
        }
    }

    public List<Author> getAfterFileId(int id)
    {
        List<Integer> ff = new ArrayList<>();
        List<Author> sectList = new ArrayList<Author>();
        try {
            String query = "SELECT * FROM legaf WHERE idf = ?";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, id);
            ResultSet result = preparedStmt.executeQuery();
            while (result.next())
            {
                ff.add(result.getInt("ida"));
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        for (Integer idrez : ff)
        {
            List<Author> rzm = new ArrayList<Author>();
            Session session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                org.hibernate.query.Query query = session.createQuery("FROM Author WHERE ida = :ida");
                query.setParameter("ida", idrez);
                rzm = query.list();
                sectList.add(rzm.get(0));
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();

            }
        }
        return sectList;
    }

    public boolean login(String username,String password)
    {
        List<Author> authors = getAllAuthor();
        Author rez = null;
        for(Author aut : authors)
            if(aut.getUsername().equals(username) && aut.getPassword().compareTo(password) == 0)
            {
                rez = aut;
            }

        if (rez!=null) {
            this.idforfile = rez.getIda();
            return true;
        }
        else
            return false;
    }
    public void save(String username, String password, String name) {
        Transaction tx = null;
        Session ses = factory.openSession();
        try{
            tx = ses.beginTransaction();
            Query query = ses.createNativeQuery("INSERT INTO Authors (username, password, name) VALUES (:username, :password, :name)");
            query.setParameter("username", username);
            query.setParameter("password", password);
            query.setParameter("name", name);
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

    public List<Files> getAllFilesAdmin()
    {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Files> sectList = new ArrayList<Files>();
        try {
            tx = session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("FROM File");
            sectList = query.list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return sectList;
        }
    }
}
