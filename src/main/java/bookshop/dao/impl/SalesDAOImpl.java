package bookshop.dao.impl;

import bookshop.dao.SalesDAO;
import bookshop.entities.Sales;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SalesDAOImpl implements SalesDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Sales> loadAllSales() {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Sales").list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Sales getSalesById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            Sales sales = session.get(Sales.class, id);
            return sales;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public boolean updateSales(Sales sales) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(sales);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean addNewSale(Sales sales) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(sales);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean deleteSale(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(getSalesById(id));
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return false;
    }
}
