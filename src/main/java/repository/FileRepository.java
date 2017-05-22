package repository;

import model.File;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mikha on 5/13/2017.
 */
public class FileRepository {
//    private Connection dbConnection;
//    public FileRepository(Connection connection) {
//        this.dbConnection = connection;
//    }
    private static SessionFactory factory;
    List<File> fileList = new ArrayList<>();

    public FileRepository(){
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
            fileList = session.createQuery("FROM File").list();
            for (Iterator iterator = fileList.iterator(); iterator.hasNext();)
            {
                File f = (File) iterator.next();
                System.out.print("Id: " + f.getIdF());
                //System.out.print("Airport: " + f.getAirport());
                //System.out.println("Destination: " + f.getDestination());
            }
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
    public void save(File f){
        fileList.add(f);
    }

    public List<File> getAll(){
        return fileList;
    }

    public File getById(int id){
        for(int i = 0 ; i < fileList.size(); i++){
            if(fileList.get(i).getIdF() == id)
                return fileList.get(i);
        }
        return null;
    }
}
