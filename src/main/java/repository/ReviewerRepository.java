package repository;

import model.CM;
import model.File;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Dragos on 5/8/2017.
 */
public class ReviewerRepository implements CRUDRepository{

    private static SessionFactory factory;
    private List<CM> reviewerList = new ArrayList<>();
    private int idLogin;
    public ReviewerRepository() {
        try {
            this.factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        Transaction tx = null;
        //aa
        try {
            tx = session.beginTransaction();
            reviewerList = session.createQuery("FROM CM").list();
            for (Iterator iterator = reviewerList.iterator(); iterator.hasNext(); ) {
                CM c = (CM) iterator.next();
                //System.out.print("Id: " + c.getId());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public boolean login(String username, String password){
        for(int i = 0; i < reviewerList.size(); i++){
            if(reviewerList.get(i).getUsername().equals(username) && reviewerList.get(i).getPassword().equals(password)) {
                setIdLogin(reviewerList.get(i).getId());
                return true;
            }
        }
        return false;
    }

    public int getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(int idLogin) {
        this.idLogin = idLogin;
    }
}
