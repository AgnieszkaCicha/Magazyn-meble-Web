package pl.agnieszkacicha.magazyn.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.agnieszkacicha.magazyn.dao.IUserDAO;
import pl.agnieszkacicha.magazyn.model.User;

import javax.persistence.NoResultException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class HibernateUserDAOImpl implements IUserDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public User getUserByLogin(String login) {

            Session session = this.sessionFactory.openSession();
            Query<User> query = session.createQuery(
                    "FROM pl.agnieszkacicha.magazyn.model.User WHERE login = :login");
            query.setParameter("login", login);
            User user = null;
        try {
            user = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Błąd: Nie znalezniono usera");
        } finally {
        session.close();}
        return user;
    }

    @Override
    public void updateUser(User user) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(user);
            tx.commit();
        }catch (Exception e) {
            if (tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void persistUser(User user) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        }catch (Exception e) {
            if (tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
    }


}
