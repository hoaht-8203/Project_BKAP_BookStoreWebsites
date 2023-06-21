package bookshop.dao;

import bookshop.entities.Orders;

import java.util.Date;
import java.util.List;

public interface OrdersDAO {
    public boolean addNewOrder(Orders orders);
    public List<Orders> loadAllOrderByStatus(Integer status);
    public List<Orders> loadAllOrder();
    public List<Orders> loadAllOrderCurrentDate();
    public List<Orders> loadOrdersByDate(Date date);
    public List<Orders> loadOrdersByDateAndStatus(Date date, Integer status);
    public List<Orders> loadOrderByStartDateAndEndDate(Date startDate, Date endDate);
    public List<Orders> loadOrderByStartDateEndDateStatus(Date startDate, Date endDate, Integer status);
    public Orders loadOrderById(Integer id);
    public boolean updateOrders(Orders orders);
    public List<Orders> getFiveDateToNow();
}
