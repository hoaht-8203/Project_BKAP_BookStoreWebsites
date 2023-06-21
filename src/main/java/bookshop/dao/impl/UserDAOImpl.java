package bookshop.dao.impl;

import bookshop.dao.UserDAO;
import bookshop.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User findByUserName(String username) {
        Session session = sessionFactory.openSession();
        try {
            User user =(User) session.createQuery("from User where username like :username").setParameter("username", username).uniqueResult();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public boolean addNewUser(User user) {
    	 Session session = sessionFactory.openSession();
         try {
        	 session.beginTransaction();
        	 session.save(user);
        	 session.getTransaction().commit();
        	 return true;
         } catch (Exception e) {
             e.printStackTrace();
         }finally {
             session.close();
         }
    	return false;
    }

    @Override
    public List<User> loadAllAccount() {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from User").list();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public User getUserById(Long id) {
        Session session = sessionFactory.openSession();
        try {
            User user = session.get(User.class, id);
            return user;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;
    }

    @Override
    public boolean updateUser(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(user);
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
    public boolean deleteUser(Long id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(getUserById(id));
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
