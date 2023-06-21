package bookshop.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Books_Details")
public class Books_Details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Publication date is cannot empty!")
    @Column(name = "Publication_date")
    private Date publication_date;
    @NotEmpty(message = "Publisher is cannot empty!")
    @Column(name = "Publishers")
    private String publishers;
    @NotNull(message = "Length of book is cannot empty!")
    @Column(name = "Length")
    private Float length;
    @NotNull(message = "Width of book is cannot empty!")
    @Column(name = "Width")
    private Float width;
    @NotNull(message = "Thickness of book is cannot empty!")
    @Column(name = "Thickness")
    private Float thickness;
    @NotNull(message = "Page number is cannot empty!")
    @Column(name = "Page_number")
    private Integer page_number;
    @NotEmpty(message = "Language is cannot empty!")
    @Column(name = "Language")
    private String language;
    @Column(name = "Available")
    private Boolean available;
    @NotNull(message = "Weight of book is cannot empty!")
    @Column(name = "Weight")
    private Float weight;
    @NotNull(message = "Price in cannot empty!")
    @Column(name = "Price")
    private Float price;
    @Column(name = "PriceAfterSale")
    private Float priceAfterSale;
    @NotNull(message = "Total Stock is cannot empty!")
    @Column(name = "QuatityStock")
    private Integer quantityStock;
    @Column(name = "quantitySold")
    private Integer quantitySold;

    @ManyToOne
    @JoinColumn(name = "Books_Id", referencedColumnName = "Id")
    private Books books;

    @NotNull(message = "Formats is cannot empty!")
    @ManyToOne
    @JoinColumn(name = "Formats_Id", referencedColumnName = "Id")
    private Formats formats;

    @OneToMany(mappedBy = "booksDetails")
    private Set<Orders_Details> ordersDetails;

    @OneToMany(mappedBy = "booksDetails")
    private Set<Wishlist> wishlists;

    public Books_Details(){

    }

    public Books_Details(Integer id, Date publication_date, String publishers, Float length, Float width, Float thickness, Integer page_number, String language, Boolean available, Float weight, Float price, Float priceAfterSale, Integer quantityStock,
                         Integer quantitySold, Books books, Formats formats, Set<Orders_Details> ordersDetails, Set<Wishlist> wishlists) {
        this.id = id;
        this.publication_date = publication_date;
        this.publishers = publishers;
        this.length = length;
        this.width = width;
        this.thickness = thickness;
        this.page_number = page_number;
        this.language = language;
        this.available = available;
        this.weight = weight;
        this.price = price;
        this.priceAfterSale = priceAfterSale;
        this.quantityStock = quantityStock;
        this.quantitySold = quantitySold;
        this.books = books;
        this.formats = formats;
        this.ordersDetails = ordersDetails;
        this.wishlists = wishlists;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(Date publication_date) {
        this.publication_date = publication_date;
    }

    public String getPublishers() {
        return publishers;
    }

    public void setPublishers(String publishers) {
        this.publishers = publishers;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getThickness() {
        return thickness;
    }

    public void setThickness(Float thickness) {
        this.thickness = thickness;
    }

    public Integer getPage_number() {
        return page_number;
    }

    public void setPage_number(Integer page_number) {
        this.page_number = page_number;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }

    public Formats getFormats() {
        return formats;
    }

    public void setFormats(Formats formats) {
        this.formats = formats;
    }

    public Integer getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(Integer quantityStock) {
        this.quantityStock = quantityStock;
    }

    public Set<Orders_Details> getOrdersDetails() {
        return ordersDetails;
    }

    public void setOrdersDetails(Set<Orders_Details> ordersDetails) {
        this.ordersDetails = ordersDetails;
    }

    public Integer getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Integer quantitySold) {
        this.quantitySold = quantitySold;
    }

    public Set<Wishlist> getWishlists() {
        return wishlists;
    }

    public void setWishlists(Set<Wishlist> wishlists) {
        this.wishlists = wishlists;
    }

    public Float getPriceAfterSale() {
        return priceAfterSale;
    }

    public void setPriceAfterSale(Float priceAfterSale) {
        this.priceAfterSale = priceAfterSale;
    }
}
