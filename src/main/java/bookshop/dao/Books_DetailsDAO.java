package bookshop.dao;

import bookshop.entities.Books;
import bookshop.entities.Books_Details;

import java.util.Date;
import java.util.List;

public interface Books_DetailsDAO {
    public List<Books_Details> getFormatsByBookId(Integer id);
    public Books_Details getBooksDetailsById(Integer id);
    public boolean addNewBooksDetails(Books_Details booksDetails);
    public boolean updateBookDetail(Books_Details booksDetails);
    public List<Books_Details> loadAllBookDetail();
    public boolean deleteBookDetail(Integer id);
    public List<Books_Details> loadListBestSell(Integer amount);
    public List<Books_Details> loadListBestSellPaging(Integer amount, Integer offset, Integer maxResult);
    public Long getTotalBestSell(Integer amount);
    public Long getTotalStockByBookId(Integer id);
    public List<Books_Details> loadListNewBookOrderPriceAsc(Integer month);
    public List<Books_Details> loadListNewBookOrderPriceDcs(Integer month);
    public List<Books_Details> getBesSellerBookPagingSortAsc(Integer amount);
    public List<Books_Details> getBesSellerBookPagingSortDcs(Integer amount);
    public List<Books_Details> loadOnSaleBookPagingSortAsc();
    public List<Books_Details> loadOnSaleBookPagingSortDcs();
    public List<Books_Details> loadAllBookByFormatId(Integer id);
}
