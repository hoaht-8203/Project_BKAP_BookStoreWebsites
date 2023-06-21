package bookshop.entities;

public class Books_Details_Cart {
    private Integer bookDetailId;
    private String bookImg;
    private String bookName;
    private Float bookPrice;
    private Integer quantity;
    private Float totalPrice;

    public Books_Details_Cart(Integer bookDetailId, String bookImg, String bookName, Float bookPrice, Integer quantity, Float totalPrice) {
        this.bookDetailId = bookDetailId;
        this.bookImg = bookImg;
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Integer getBookDetailId() {
        return bookDetailId;
    }

    public void setBookDetailId(Integer bookDetailId) {
        this.bookDetailId = bookDetailId;
    }

    public String getBookImg() {
        return bookImg;
    }

    public void setBookImg(String bookImg) {
        this.bookImg = bookImg;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Float getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Float bookPrice) {
        this.bookPrice = bookPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
