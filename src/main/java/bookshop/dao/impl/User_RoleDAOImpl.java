package bookshop.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookshop.dao.User_RoleDAO;
import bookshop.entities.User_Role;

import java.util.List;

@Repository
public class User_RoleDAOImpl implements User_RoleDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean addNewUserRole(User_Role user_Role) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(user_Role);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return false;
	}

	@Override
	public List<User_Role> loadAccByRoleId(Long id) {
		Session session = sessionFactory.openSession();
		try {
			List list = session.createQuery("from User_Role where role.id like :id").setParameter("id", id).list();
			return list;
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			session.close();
		}

		return null;
	}

	@Override
	public List<User_Role> loadAccByUserId(Long id) {
		Session session = sessionFactory.openSession();
		try {
			List list = session.createQuery("from User_Role where user.id = :id")
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
	public boolean deleteUserRole(User_Role ur) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.delete(ur);
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
