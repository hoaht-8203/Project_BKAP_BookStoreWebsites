package bookshop.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Sales")
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @NotEmpty(message = "Sales name is cannot empty!")
    @Column(name = "Name")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Start date is cannot empty!")
    @Column(name = "Start_date")
    private Date start_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "End date is cannot empty!")
    @Column(name = "End_date")
    private Date end_date;
    @NotNull(message = "Percentage is cannot empty!")
    @Column(name = "Percentage")
    private Float percentage;
    @Column(name = "SaleImg")
    private String saleImg;
    @Column(name = "TotalBook")
    private Integer totalBook;
    @Column(name = "Status")
    private Boolean status;
    @OneToMany(mappedBy = "sales")
    private Set<Books> books;

    public Sales(){

    }

    public Sales(Integer id, String name, Date start_date, Date end_date,
                 Float percentage, String saleImg, Integer totalBook, Boolean status, Set<Books> books) {
        this.id = id;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.percentage = percentage;
        this.saleImg = saleImg;
        this.totalBook = totalBook;
        this.status = status;
        this.books = books;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public Integer getTotalBook() {
        return totalBook;
    }

    public void setTotalBook(Integer totalBook) {
        this.totalBook = totalBook;
    }

    public Set<Books> getBooks() {
        return books;
    }

    public void setBooks(Set<Books> books) {
        this.books = books;
    }
    public String getSaleImg() {
        return saleImg;
    }
    public void setSaleImg(String saleImg) {
        this.saleImg = saleImg;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
