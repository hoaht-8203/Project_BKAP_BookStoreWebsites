package bookshop.dao.impl;

import bookshop.dao.GenresDAO;
import bookshop.entities.Genres;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenresDAOImpl implements GenresDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Genres> loadAllGenres() {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Genres").list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Genres getGenreById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            Genres genres = session.get(Genres.class, id);
            return genres;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public boolean addNewGenre(Genres genres) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(genres);
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
    public boolean deleteGenre(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(getGenreById(id));
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
    public boolean updateGenre(Genres genres) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(genres);
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
