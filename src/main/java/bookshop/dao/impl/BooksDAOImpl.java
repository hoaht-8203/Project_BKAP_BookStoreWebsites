package bookshop.dao.impl;

import bookshop.dao.BooksDAO;
import bookshop.entities.Books;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public class BooksDAOImpl implements BooksDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Books> loadAllBooks() {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books").list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Books getDetailsBookById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            Books books = session.get(Books.class, id);
            return books;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public boolean addNewBook(Books books) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(books);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean updateBook(Books books) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(books);
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
    public boolean deleteBook(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(getDetailsBookById(id));
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
    public List<Books> getBookBySaleId(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books where sales.id like :id").setParameter("id", id).list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Books> loadNewestBookByMonth(Integer month) {
        LocalDate currentDate = LocalDate.now();
        LocalDate monthAgo = currentDate.minusMonths(month);

        Session session = sessionFactory.openSession();
        try {
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
            Date dateMonthAgo = sf.parse(monthAgo.getDayOfMonth()+"/"+monthAgo.getMonthValue()+"/"+monthAgo.getYear());
            List list = session.createQuery("from Books where create_date >= :monthAgo")
                    .setParameter("monthAgo", dateMonthAgo).list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public List<Books> loadNewestBookByMonthPaging(Integer month, Integer offset, Integer maxResult) {
        LocalDate currentDate = LocalDate.now();
        LocalDate monthAgo = currentDate.minusMonths(month);

        Session session = sessionFactory.openSession();
        try {
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
            Date dateMonthAgo = sf.parse(monthAgo.getDayOfMonth()+"/"+monthAgo.getMonthValue()+"/"+monthAgo.getYear());
            List list = session.createQuery("from Books where create_date >= :monthAgo")
                    .setParameter("monthAgo", dateMonthAgo)
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
    public Long getTotalNewestBookByMonth(Integer month) {
        LocalDate currentDate = LocalDate.now();
        LocalDate monthAgo = currentDate.minusMonths(month);

        Session session = sessionFactory.openSession();
        try {
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
            Date dateMonthAgo = sf.parse(monthAgo.getDayOfMonth()+"/"+monthAgo.getMonthValue()+"/"+monthAgo.getYear());
            List list = session.createQuery("select count(*) from Books where create_date >= :monthAgo")
                    .setParameter("monthAgo", dateMonthAgo)
                    .list();
            return (Long) list.get(0);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return 0L;
    }

    @Override
    public List<Books> loadOnSaleBook() {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books where sales.id != null ").list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Books> loadOnSaleBookPaging(Integer offset, Integer maxResult) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books where sales.id != null ")
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
    public Long getTotalOnSaleBook() {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("select count(*) from Books where sales.id != null ")
                    .list();
            return (Long) list.get(0);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return 0L;
    }

    @Override
    public Long getTotalBookByDiscountId(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("select count(*) from Books where discount.id like :id")
                    .setParameter("id", id)
                    .list();
            return (Long) list.get(0);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return 0L;
    }

    @Override
    public Long getTotalBookBySaleId(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("select count(*) from Books where sales.id like :id")
                    .setParameter("id", id)
                    .list();
            return (Long) list.get(0);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return 0L;
    }

    @Override
    public List<Books> getBookByDiscountId(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books where discount.id like :id")
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

    @Override
    public List<Books> loadBookNullDiscount() {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books where discount.id = null").list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public List<Books> loadBookNullSale() {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books where sales.id = null").list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public List<Books> loadBookByAuthorIdPaging(Integer authorId, Integer offset, Integer maxResult) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books where authors.id = :authorId")
                    .setParameter("authorId", authorId)
                    .setFirstResult(offset)
                    .setMaxResults(maxResult).list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public Long getTotalBookByAuthorId(Integer authorId) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("select count(*) from Books where authors.id = :authorId")
                    .setParameter("authorId", authorId).list();
            return (Long) list.get(0);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public List<Books> getBesSellerBookPaging(Integer amount, Integer offset, Integer maxResult) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books where totalSold >= :amount")
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
    public Long getTotalBestSellBook(Integer amount) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("select count(*) from Books where totalSold >= :amount")
                    .setParameter("amount", amount).list();
            return (Long) list.get(0);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public List<Books> findBookByTitleOrAuthorNamePaging(String search, Integer offset, Integer maxResult) {
        Session session = sessionFactory.openSession();
        try {
            if (search.length() == 0){
                search = "%";
            }else {
                search = "%" + search + "%";
            }

            List list = session.createQuery("from Books where lower(title) like lower(:title) " +
                            "or lower(authors.fullName) like lower(:authorName)")
                    .setParameter("title", search.toLowerCase())
                    .setParameter("authorName", search.toLowerCase())
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
    public Long totalBookFindBookByTitleOrAuthorNamePaging(String search) {
        Session session = sessionFactory.openSession();
        try {
            if (search.length() == 0){
                search = "%";
            }else {
                search = "%" + search + "%";
            }

            List list = session.createQuery("select count(*) from Books where lower(title) like lower(:title) " +
                            "or lower(authors.fullName) like lower(:authorName)")
                    .setParameter("title", search.toLowerCase())
                    .setParameter("authorName", search.toLowerCase())
                    .list();
            return (Long) list.get(0);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return 0L;
    }

    @Override
    public List<Books> loadNewestBookByMonthPagingSortAtoZ(Integer month, Integer offset, Integer maxResult) {
        LocalDate currentDate = LocalDate.now();
        LocalDate monthAgo = currentDate.minusMonths(month);

        Session session = sessionFactory.openSession();
        try {
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
            Date dateMonthAgo = sf.parse(monthAgo.getDayOfMonth()+"/"+monthAgo.getMonthValue()+"/"+monthAgo.getYear());
            List list = session.createQuery("from Books where create_date >= :monthAgo order by substring(title, 1, 1) ASC, title asc")
                    .setParameter("monthAgo", dateMonthAgo)
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
    public List<Books> loadAllBookByAuthorId(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books where authors.id = :id")
                    .setParameter("id", id).list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }
}
