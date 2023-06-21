package bookshop.dao.impl;

import bookshop.dao.AuthorDAO;
import bookshop.entities.Authors;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorDAOImpl implements AuthorDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Authors> loadAllAuthors() {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Authors").list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public boolean addNewAuthor(Authors authors) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(authors);
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
    public Authors getAuthorById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            Authors authors = session.get(Authors.class, id);
            return authors;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public boolean deleteAuthor(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(getAuthorById(id));
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
    public boolean updateAuthor(Authors authors) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(authors);
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
