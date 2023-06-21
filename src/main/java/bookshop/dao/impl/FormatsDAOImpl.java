package bookshop.dao.impl;

import bookshop.dao.FormatsDAO;
import bookshop.entities.Formats;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FormatsDAOImpl implements FormatsDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Formats> loadALlFormats() {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Formats").list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Formats getFormatById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            Formats formats = session.get(Formats.class, id);
            return formats;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public boolean addNewFormat(Formats formats) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(formats);
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
    public boolean deleteFormatById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(getFormatById(id));
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
    public boolean updateFormat(Formats formats) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(formats);
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
