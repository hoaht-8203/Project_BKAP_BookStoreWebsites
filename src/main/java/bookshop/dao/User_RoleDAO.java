package bookshop.dao;

import bookshop.entities.User_Role;

import java.util.List;

public interface User_RoleDAO {
	public boolean addNewUserRole(User_Role user_Role);
	public List<User_Role> loadAccByRoleId(Long id);
	public List<User_Role> loadAccByUserId(Long id);
	public boolean deleteUserRole(User_Role ur);
}
