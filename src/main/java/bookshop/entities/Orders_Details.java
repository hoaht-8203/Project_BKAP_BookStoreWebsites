package bookshop.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Orders_Details")
public class Orders_Details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Quantity")
    private Integer quantity;
    @Column(name = "TotalPrice")
    private Float totalPrice;

    @ManyToOne()
    @JoinColumn(name = "Order_Id", referencedColumnName = "Id")
    private Orders orders;

    @ManyToOne()
    @JoinColumn(name = "Detail_Book_Id", referencedColumnName = "Id")
    private Books_Details booksDetails;

    public Orders_Details() {
    }

    public Orders_Details(Integer id, Integer quantity, Float totalPrice, Orders orders, Books_Details booksDetails) {
        this.id = id;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orders = orders;
        this.booksDetails = booksDetails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Books_Details getBooksDetails() {
        return booksDetails;
    }

    public void setBooksDetails(Books_Details booksDetails) {
        this.booksDetails = booksDetails;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
