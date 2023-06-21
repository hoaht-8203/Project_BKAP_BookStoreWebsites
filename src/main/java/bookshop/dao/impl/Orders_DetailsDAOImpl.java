package bookshop.dao.impl;

import bookshop.dao.Orders_DetailsDAO;
import bookshop.entities.Orders_Details;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Orders_DetailsDAOImpl implements Orders_DetailsDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean addNewOrderDetail(Orders_Details ordersDetails) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(ordersDetails);
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
    public List<Orders_Details> loadOrdersDetailsByOrderId(Integer orderId) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Orders_Details where orders.id = :id")
                    .setParameter("id", orderId).list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public List<Orders_Details> loadOrderDetailByBookDetailId(Integer bookDetailId) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Orders_Details where booksDetails.id = :id")
                    .setParameter("id", bookDetailId).list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public List<Orders_Details> loadAllOrdersDetails() {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Orders_Details").list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public Orders_Details getOrderDetailById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            Orders_Details ordersDetails = session.get(Orders_Details.class, id);
            return ordersDetails;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public boolean updateOrderDetail(Orders_Details ordersDetails) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(ordersDetails);
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
    public boolean deleteOrderDetailById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(getOrderDetailById(id));
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            session.getTransaction().commit();
            e.printStackTrace();
        }finally {
            session.close();
        }

        return false;
    }
}
