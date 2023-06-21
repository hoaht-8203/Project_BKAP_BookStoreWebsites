package bookshop.sub;

public class Book_Detail_Category {
    private Integer book_Id;
    private String bookName;
    private String authorName;
    private Float price;
    private Float priceAfterSale;

    public Book_Detail_Category() {
    }

    public Book_Detail_Category(Integer book_Id, String bookName, String authorName, Float price, Float priceAfterSale) {
        this.book_Id = book_Id;
        this.bookName = bookName;
        this.authorName = authorName;
        this.price = price;
        this.priceAfterSale = priceAfterSale;
    }

    public Integer getBook_Id() {
        return book_Id;
    }

    public void setBook_Id(Integer book_Id) {
        this.book_Id = book_Id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPriceAfterSale() {
        return priceAfterSale;
    }

    public void setPriceAfterSale(Float priceAfterSale) {
        this.priceAfterSale = priceAfterSale;
    }
}
