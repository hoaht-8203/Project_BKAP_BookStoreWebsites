package bookshop.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @NotEmpty(message = "Full Name is cannot empty!")
    @Column(name = "FullName")
    private String fullName;
    @NotEmpty(message = "Email is cannot empty!")
    @Column(name = "Email")
    private String email;
    @NotEmpty(message = "Phone number is cannot empty")
    @Column(name = "PhoneNumber")
    private String phoneNumber;
    @NotEmpty(message = "Address is cannot empty!")
    @Column(name = "Address")
    private String address;
    @Column(name = "TypeShip")
    private String typeShip;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "OrderDate")
    private Date orderDate;
    //   0 chua xu ly
    //   -1 khong xu ly
    //   3 Pre-order
    //   2 dang rao hang
    //   1 rao hang thanh cong
    //  -2 rao hang k thanh cong
    @Column(name = "TotalPriceOrder")
    private Float totalPriceOrder;
    @Column(name = "Status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "User_Id", referencedColumnName = "Id")
    private User user;

    @OneToMany(mappedBy = "orders")
    private Set<Orders_Details> ordersDetails;

    public Orders() {
    }

    public Orders(Integer id, String fullName, String email, String phoneNumber, String address, String typeShip, Date orderDate, Float totalPriceOrder, Integer status, User user, Set<Orders_Details> ordersDetails) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.typeShip = typeShip;
        this.orderDate = orderDate;
        this.totalPriceOrder = totalPriceOrder;
        this.status = status;
        this.user = user;
        this.ordersDetails = ordersDetails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTypeShip() {
        return typeShip;
    }

    public void setTypeShip(String typeShip) {
        this.typeShip = typeShip;
    }

    public Set<Orders_Details> getOrdersDetails() {
        return ordersDetails;
    }

    public void setOrdersDetails(Set<Orders_Details> ordersDetails) {
        this.ordersDetails = ordersDetails;
    }

    public Float getTotalPriceOrder() {
        return totalPriceOrder;
    }

    public void setTotalPriceOrder(Float totalPriceOrder) {
        this.totalPriceOrder = totalPriceOrder;
    }
}
