package bookshop.dao;

import bookshop.entities.Sales;

import java.util.List;

public interface SalesDAO {
    public List<Sales> loadAllSales();
    public Sales getSalesById(Integer id);
    public boolean updateSales(Sales sales);
    public boolean addNewSale(Sales sales);
    public boolean deleteSale(Integer id);
}
