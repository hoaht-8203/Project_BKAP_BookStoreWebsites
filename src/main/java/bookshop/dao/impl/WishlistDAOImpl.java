package bookshop.dao.impl;

import bookshop.dao.WishlistDAO;
import bookshop.entities.Wishlist;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishlistDAOImpl implements WishlistDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean addNewWishlist(Wishlist wishlist) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(wishlist);
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
    public List<Wishlist> loadAllWishlistByUserId(Long userId) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Wishlist where user.id like :id")
                    .setParameter("id", userId).list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public Wishlist getWishlistById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            Wishlist wishlist = session.get(Wishlist.class, id);
            return wishlist;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public Wishlist getWishListByIdAndUserId(Integer id, Long userId) {
        Session session = sessionFactory.openSession();
        try {
            Wishlist wishlist = (Wishlist) session.createQuery("from Wishlist where id = :id and user.id = :userId")
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .uniqueResult();
            return wishlist;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public boolean deleteWishlistById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(getWishlistById(id));
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
    public boolean deleteWishlist(Wishlist wishlist) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(wishlist);
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
    public Wishlist getWishlistByUserIdAndBookId(Long userId, Integer bookId) {
        Session session = sessionFactory.openSession();
        try {
            Wishlist wishlist = (Wishlist) session.createQuery("from Wishlist where user.id = :userId and booksDetails.id = :bookId")
                    .setParameter("userId", userId)
                    .setParameter("bookId", bookId)
                    .uniqueResult();
            return wishlist;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }
}
