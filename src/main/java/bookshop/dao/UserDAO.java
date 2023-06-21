package bookshop.dao;

import bookshop.entities.User;

import java.util.List;

public interface UserDAO {
    public User findByUserName(String username);
    public boolean addNewUser(User user);
    public List<User> loadAllAccount();
    public User getUserById(Long id);
    public boolean updateUser(User user);
    public boolean deleteUser(Long id);
}
