package repository;

import model.Attendant;
import model.Conference;
import model.Sections;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Cosmin on 4/6/2017.
 */
public class AttendantRepository implements CRUDRepository {
    private static SessionFactory factory;
    private int idLogin;
    private List<Attendant> attendantList = new ArrayList<>();

    public AttendantRepository() {
        try{
            this.factory = new Configuration().configure().buildSessionFactory();
        }
        catch(Throwable ex){
            System.err.println("Failed to create sessionfactory object " + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session ses = factory.openSession();
        Transaction tx = null;
        try{
            tx = ses.beginTransaction();
            attendantList = ses.createQuery("FROM Attendant").list();
            for (Iterator i = attendantList.iterator(); i.hasNext();){
                Attendant a = (Attendant) i.next();
            }
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

    public int getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(int idLogin) {
        this.idLogin = idLogin;
    }

    public boolean login(String username, String password) {
        if (attendantList.size() != 0){
            for (int i = 0; i < attendantList.size(); i++){
                if (attendantList.get(i).getUsername().equals(username) && attendantList.get(i).getPassword().equals(password)){
                    setIdLogin(attendantList.get(i).getIdat());
                    return true;
                }
            }
        }
        return false;
    }

    public void partikip(int id){
        Transaction tx = null;
        boolean ret = false;
        Session ses = factory.openSession();
        try{
            tx = ses.beginTransaction();
            Query query = ses.createNativeQuery("UPDATE Conference SET noparticipants = noparticipants + 1 WHERE idconf = :id");
            query.setParameter("id", id);
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

    public void save(String username, String password) {
        Transaction tx = null;
        boolean ret = false;
        Session ses = factory.openSession();
        try {
            tx = ses.beginTransaction();
            Query query = ses.createNativeQuery("INSERT INTO Attendant (username, password) VALUES (:username, :password)");
            query.setParameter("username", username);
            query.setParameter("password", password);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        } finally {
            ses.close();
        }
    }
}