package bookshop.dao;

import bookshop.entities.Books_Genres;

import java.util.List;

public interface Books_GenresDAO {
    public List<Books_Genres> loadBooksGenresByBookId(Integer id);
    public boolean addNewBookGenres(Books_Genres booksGenres);
    public boolean updateBookGenres(Books_Genres booksGenres);
    public List<Books_Genres> getBookGenresByBookId(Integer id);
    public boolean deleteBookGenresByBookId(Books_Genres booksGenres);
    public boolean deleteBookGenre(Integer id);
    public List<Books_Genres> loadAllBookGenre();
    public List<Books_Genres> loadBookGenresByGenreIdPaging(Integer id, Integer offset, Integer maxResult);
    public List<Books_Genres> sortBookGenresByGenreIdPagingPriceAsc(Integer id, Integer offset, Integer maxResult);
    public Long getTotalBookGenreByGenreId(Integer id);
    public List<Books_Genres> findBookGenreByNamePaging(String genreName, Integer offset, Integer maxResult);
    public Long getTotalBookGenreByName(String genreName);
    public List<Books_Genres> loadAllBookByGenreId(Integer id);
}
