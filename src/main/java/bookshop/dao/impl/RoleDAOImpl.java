package bookshop.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookshop.dao.RoleDAO;
import bookshop.entities.Role;

@Repository
public class RoleDAOImpl implements RoleDAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Role getRoleByName(String name) {
		Session session = sessionFactory.openSession();
		try {
			Role role = (Role) session.createQuery("from Role where name like :name").setParameter("name", name).uniqueResult();
			return role;
		} catch (Exception e) {
			e.printStackTrace();			
		}finally {
			session.close();
		}
		return null;
	}
}
