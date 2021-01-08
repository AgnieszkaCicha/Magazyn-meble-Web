package pl.agnieszkacicha.magazyn.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.agnieszkacicha.magazyn.dao.IOrderDAO;
import pl.agnieszkacicha.magazyn.model.Order;

import java.util.List;

@Repository
public class HibernateOrderDAOImpl implements IOrderDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistOrder(Order order) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save((order));
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Order> getOrderByUser(int userId) {
        Session session = this.sessionFactory.openSession();
        Query<Order> query = session.createQuery("FROM pl.agnieszkacicha.magazyn.model.Order WHERE user_id = :user_id");
        query.setParameter("user_id", userId);
        List<Order> orders = query.getResultList();
        session.close();
        return orders;
    }
}
