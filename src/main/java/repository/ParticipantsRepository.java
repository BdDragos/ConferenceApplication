package repository;

import model.Admin;
import model.Participants;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by Cosmin on 6/4/2017.
 */
public class ParticipantsRepository implements CRUDRepository {
    private SessionFactory factory;
    public ParticipantsRepository(SessionFactory factory)
    {
        this.factory = factory;
    }
    public List<Participants> getAll() {
        List<Participants> lusers = null;
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Participants ");
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
}
