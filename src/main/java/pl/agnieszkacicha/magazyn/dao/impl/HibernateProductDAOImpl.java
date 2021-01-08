package pl.agnieszkacicha.magazyn.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.agnieszkacicha.magazyn.dao.IProductDAO;
import pl.agnieszkacicha.magazyn.model.Product;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class HibernateProductDAOImpl implements IProductDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Product getProductByCode(String code) {
        Session session = this.sessionFactory.openSession();
        Query<Product> query = session.createQuery(
                "FROM pl.agnieszkacicha.magazyn.model.Product WHERE code = :code");
        query.setParameter("code", code);

        Product product = null;
        try {
            product = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Błąd: Nie znaleziono produktu");
        }
        session.close();
        return product;
    }

    @Override
    public void updateProduct(Product product) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(product);
            tx.commit();
        }catch (Exception e) {
            if (tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void persistProduct(Product product) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(product);
            tx.commit();
        }catch (Exception e) {
            if (tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public Product getProductById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<Product> query = session.createQuery(
                "FROM pl.agnieszkacicha.magazyn.model.Product WHERE id = :id");
        query.setParameter("id", id);
        Product product = null;
                try {
                    product = query.getSingleResult();
                } catch (NoResultException e) {
                    System.out.println("Nie znaleziono produktu");
                }
        session.close();
        return product;
    }

    @Override
    public List<Product> getProductsByCategory(Product.Category category) {
        Session session = this.sessionFactory.openSession();
        Query<Product> query = session.createQuery(
                "FROM pl.agnieszkacicha.magazyn.model.Product WHERE category = :category");
        query.setParameter("category", category);
        List<Product> products = query.getResultList();
        session.close();
        return products;
    }

    @Override
    public List<Product> getAllProducts() {
        Session session = this.sessionFactory.openSession();
        Query<Product> query = session.createQuery(
                "FROM pl.agnieszkacicha.magazyn.model.Product");
        List<Product> products = query.getResultList();
        session.close();
        return products;
    }
}
