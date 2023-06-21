package bookshop.dao;

import bookshop.entities.Orders_Details;

import java.util.List;

public interface Orders_DetailsDAO {
    public boolean addNewOrderDetail(Orders_Details ordersDetails);
    public List<Orders_Details> loadOrdersDetailsByOrderId(Integer orderId);
    public List<Orders_Details> loadOrderDetailByBookDetailId(Integer bookDetailId);
    public List<Orders_Details> loadAllOrdersDetails();
    public Orders_Details getOrderDetailById(Integer id);
    public boolean updateOrderDetail(Orders_Details ordersDetails);
    public boolean deleteOrderDetailById(Integer id);
}
