package bookshop.dao;

import bookshop.entities.Books;

import java.util.List;

public interface BooksDAO {
    public List<Books> loadAllBooks();
    public Books getDetailsBookById(Integer id);
    public boolean addNewBook(Books books);
    public boolean updateBook(Books books);
    public boolean deleteBook(Integer id);
    public List<Books> getBookBySaleId(Integer id);
    public List<Books> loadNewestBookByMonth(Integer month);
    public List<Books> loadNewestBookByMonthPaging(Integer month, Integer offset, Integer maxResult);
    public Long getTotalNewestBookByMonth(Integer month);
    public List<Books> loadOnSaleBook();
    public List<Books> loadOnSaleBookPaging(Integer offset, Integer maxResult);
    public Long getTotalOnSaleBook();
    public Long getTotalBookByDiscountId(Integer id);
    public Long getTotalBookBySaleId(Integer id);
    public List<Books> getBookByDiscountId(Integer id);
    public List<Books> loadBookNullDiscount();
    public List<Books> loadBookNullSale();
    public List<Books> loadBookByAuthorIdPaging(Integer authorId, Integer offset, Integer maxResult);
    public Long getTotalBookByAuthorId(Integer authorId);
    public List<Books> getBesSellerBookPaging(Integer amount, Integer offset, Integer maxResult);
    public Long getTotalBestSellBook(Integer amount);
    public List<Books> findBookByTitleOrAuthorNamePaging(String search, Integer offset, Integer maxResult);
    public Long totalBookFindBookByTitleOrAuthorNamePaging(String search);
    public List<Books> loadNewestBookByMonthPagingSortAtoZ(Integer month, Integer offset, Integer maxResult);
    public List<Books> loadAllBookByAuthorId(Integer id);
}
