package bookshop.dao;

import bookshop.entities.Authors;

import java.util.List;

public interface AuthorDAO {
    public List<Authors> loadAllAuthors();
    public boolean addNewAuthor(Authors authors);
    public Authors getAuthorById(Integer id);
    public boolean deleteAuthor(Integer id);
    public boolean updateAuthor(Authors authors);
}
