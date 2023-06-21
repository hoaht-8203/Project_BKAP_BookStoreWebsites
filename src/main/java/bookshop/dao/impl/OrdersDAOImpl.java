package bookshop.dao.impl;

import bookshop.dao.OrdersDAO;
import bookshop.entities.Orders;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public class OrdersDAOImpl implements OrdersDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean addNewOrder(Orders orders) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(orders);
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
    public List<Orders> loadAllOrderByStatus(Integer status) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Orders where status = :status")
                    .setParameter("status", status).list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Orders> loadAllOrder() {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Orders").list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public List<Orders> loadAllOrderCurrentDate() {
        LocalDate currentDate = LocalDate.now();

        Session session = sessionFactory.openSession();
        try {
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sf.parse(currentDate.getDayOfMonth()+"/"+currentDate.getMonthValue()+"/"+currentDate.getYear());
            List list = session.createQuery("from Orders where orderDate = :currentDate")
                    .setParameter("currentDate", date).list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public List<Orders> loadOrdersByDate(Date date) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Orders where orderDate = :date", Orders.class)
                    .setParameter("date", date).list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public List<Orders> loadOrdersByDateAndStatus(Date date, Integer status) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Orders where orderDate = :date and status = :status")
                    .setParameter("date", date)
                    .setParameter("status", status).list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public List<Orders> loadOrderByStartDateAndEndDate(Date startDate, Date endDate) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Orders where orderDate >= :starDate and orderDate <= :endDate")
                    .setParameter("starDate", startDate)
                    .setParameter("endDate", endDate)
                    .list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public List<Orders> loadOrderByStartDateEndDateStatus(Date startDate, Date endDate, Integer status) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Orders where orderDate >= :starDate and orderDate <= :endDate and status = :status")
                    .setParameter("starDate", startDate)
                    .setParameter("endDate", endDate)
                    .setParameter("status", status)
                    .list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public Orders loadOrderById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            Orders orders = session.get(Orders.class, id);
            return orders;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public boolean updateOrders(Orders orders) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(orders);
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
    public List<Orders> getFiveDateToNow() {
        LocalDate currentDate = LocalDate.now();
        LocalDate lessFiveDate = currentDate.minusDays(5);

        Session session = sessionFactory.openSession();
        try {
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sf.parse(currentDate.getDayOfMonth()+"/"+currentDate.getMonthValue()+"/"+currentDate.getYear());
            Date date2 = sf.parse(lessFiveDate.getDayOfMonth()+"/"+lessFiveDate.getMonthValue()+"/"+lessFiveDate.getYear());
            List list = session.createQuery("from Orders where orderDate >= :starDate and orderDate <= :endDate")
                    .setParameter("starDate", date2)
                    .setParameter("endDate", date)
                    .list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }
}
