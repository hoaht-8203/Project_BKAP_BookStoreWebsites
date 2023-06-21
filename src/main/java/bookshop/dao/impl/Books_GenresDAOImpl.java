package bookshop.dao.impl;

import bookshop.dao.Books_GenresDAO;
import bookshop.entities.Books_Genres;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Books_GenresDAOImpl implements Books_GenresDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Books_Genres> loadBooksGenresByBookId(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books_Genres where books.id like :id").setParameter("id", id).list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public boolean addNewBookGenres(Books_Genres booksGenres) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(booksGenres);
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
    public boolean updateBookGenres(Books_Genres booksGenres) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(booksGenres);
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
    public List<Books_Genres> getBookGenresByBookId(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books_Genres where books.id like :id").setParameter("id", id).list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public boolean deleteBookGenresByBookId(Books_Genres booksGenres) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(booksGenres);
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
    public boolean deleteBookGenre(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(getBookGenresByBookId(id));
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
    public List<Books_Genres> loadAllBookGenre() {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books_Genres").list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Books_Genres> loadBookGenresByGenreIdPaging(Integer id, Integer offset, Integer maxResult) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books_Genres where genres.id like :id")
                        .setParameter("id", id)
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
    public List<Books_Genres> sortBookGenresByGenreIdPagingPriceAsc(Integer id, Integer offset, Integer maxResult) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books_Genres where genres.id like :id order by books.")
                    .setParameter("id", id)
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
    public Long getTotalBookGenreByGenreId(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("select count(*) from Books_Genres where genres.id = :id")
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
    public List<Books_Genres> findBookGenreByNamePaging(String genreName, Integer offset, Integer maxResult) {
        Session session = sessionFactory.openSession();
        try {
            if (genreName.length() == 0){
                genreName = "%";
            }else {
                genreName = "%" + genreName + "%";
            }

            List list = session.createQuery("from Books_Genres where lower(genres.name) like lower(:genreName)")
                    .setParameter("genreName", genreName.toLowerCase())
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
    public Long getTotalBookGenreByName(String genreName) {
        Session session = sessionFactory.openSession();
        try {
            if (genreName.length() == 0){
                genreName = "%";
            }else {
                genreName = "%" + genreName + "%";
            }

            List list = session.createQuery("select count(*) from Books_Genres where lower(genres.name) like lower(:genreName)")
                    .setParameter("genreName", genreName.toLowerCase())
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
    public List<Books_Genres> loadAllBookByGenreId(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Books_Genres where genres.id = :id")
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
