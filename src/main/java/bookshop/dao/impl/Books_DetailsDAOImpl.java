package bookshop.dao.impl;

import bookshop.dao.Books_DetailsDAO;
import bookshop.entities.Books;
import bookshop.entities.Books_Details;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Repository
public class Books_DetailsDAOImpl implements Books_DetailsDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Books_Details> getFormatsByBookId(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books_Details where books.id like :id")
                    .setParameter("id", id).list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public Books_Details getBooksDetailsById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            Books_Details booksDetails = session.get(Books_Details.class, id);
            return booksDetails;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public boolean addNewBooksDetails(Books_Details booksDetails) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(booksDetails);
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
    public boolean updateBookDetail(Books_Details booksDetails) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(booksDetails);
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
    public List<Books_Details> loadAllBookDetail() {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books_Details").list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public boolean deleteBookDetail(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(getBooksDetailsById(id));
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
    public List<Books_Details> loadListBestSell(Integer amount) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books_Details where quantitysold >= :amount")
                    .setParameter("amount", amount).list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Books_Details> loadListBestSellPaging(Integer amount, Integer offset, Integer maxResult) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books_Details where quantitysold >= :amount")
                                .setParameter("amount", amount)
                                .setFirstResult(offset)
                                .setMaxResults(maxResult)
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
    public Long getTotalBestSell(Integer amount) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("select count(*) from Books_Details where quantitysold >= :amount")
                    .setParameter("amount", amount)
                    .list();
            return (Long) list.get(0);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Long getTotalStockByBookId(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("select count(*) from Books_Details where books.id like :id")
                    .setParameter("id", id).list();
            return (Long) list.get(0);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public List<Books_Details> loadListNewBookOrderPriceAsc(Integer month) {
        Session session = sessionFactory.openSession();

        LocalDate currentDate = LocalDate.now();
        LocalDate monthAgo = currentDate.minusMonths(month);
        try {
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
            Date dateMonthAgo = sf.parse(monthAgo.getDayOfMonth()+"/"+monthAgo.getMonthValue()+"/"+monthAgo.getYear());
            List list = session.createQuery(" from Books_Details bd where books.create_date >= :monthAgo " +
                            "order by priceAfterSale asc")
                    .setParameter("monthAgo", dateMonthAgo)
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
    public List<Books_Details> loadListNewBookOrderPriceDcs(Integer month) {
        Session session = sessionFactory.openSession();

        LocalDate currentDate = LocalDate.now();
        LocalDate monthAgo = currentDate.minusMonths(month);
        try {
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
            Date dateMonthAgo = sf.parse(monthAgo.getDayOfMonth()+"/"+monthAgo.getMonthValue()+"/"+monthAgo.getYear());
            List list = session.createQuery(" from Books_Details bd where books.create_date >= :monthAgo " +
                            "order by priceAfterSale desc")
                    .setParameter("monthAgo", dateMonthAgo)
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
    public List<Books_Details> getBesSellerBookPagingSortAsc(Integer amount) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books_Details where books.totalSold >= :amount " +
                            "order by priceAfterSale asc")
                    .setParameter("amount", amount)
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
    public List<Books_Details> getBesSellerBookPagingSortDcs(Integer amount) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books_Details where books.totalSold >= :amount " +
                            "order by priceAfterSale desc")
                    .setParameter("amount", amount)
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
    public List<Books_Details> loadOnSaleBookPagingSortAsc() {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books_Details where books.sales != null " +
                            "order by priceAfterSale asc")
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
    public List<Books_Details> loadOnSaleBookPagingSortDcs() {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books_Details where books.sales != null " +
                            "order by priceAfterSale desc")
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
    public List<Books_Details> loadAllBookByFormatId(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books_Details where formats.id = :id")
                    .setParameter("id", id)
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
