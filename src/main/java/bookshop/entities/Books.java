package bookshop.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @NotEmpty(message = "Title is cannot empty!")
    @Column(name = "Title")
    private String title;
    @NotEmpty(message = "Description title is cannot empty!")
    @Column(name = "Description_title")
    private String description_title;
    @NotEmpty(message = "Description detail is cannot empty!")
    @Column(name = "Description_detail")
    private String description_detail;
    @Column(name = "Image")
    private String image;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Create_date")
    private Date create_date;
    @Column(name = "TotalFormat")
    private Integer totalFormat;
    @Column(name = "TotalStock", columnDefinition = "integer default 0")
    private Integer totalStock;
    @Column(name = "TotalSold")
    private Integer totalSold;

    @ManyToOne
    @JoinColumn(name = "Author_Id", referencedColumnName = "id")
    private Authors authors;

    @ManyToOne
    @JoinColumn(name = "Sale_Id", referencedColumnName = "id")
    private Sales sales;

    @OneToMany(mappedBy = "books")
    private Set<Books_Details> booksDetails;

    @OneToMany(mappedBy = "books")
    private Set<Books_Genres> booksGenres;

    @OneToMany(mappedBy = "books")
    private Set<Reviews> reviews;

    public Books(){

    }

    public Books(Integer id, String title, String description_title, String description_detail, String image, Date create_date, Integer totalFormat, Integer totalStock, Integer totalSold, Authors authors,
                 Sales sales, Set<Books_Details> booksDetails, Set<Books_Genres> booksGenres, Set<Reviews> reviews) {
        this.id = id;
        this.title = title;
        this.description_title = description_title;
        this.description_detail = description_detail;
        this.image = image;
        this.create_date = create_date;
        this.totalFormat = totalFormat;
        this.totalStock = totalStock;
        this.totalSold = totalSold;
        this.authors = authors;
        this.sales = sales;
        this.booksDetails = booksDetails;
        this.booksGenres = booksGenres;
        this.reviews = reviews;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription_title() {
        return description_title;
    }

    public void setDescription_title(String description_title) {
        this.description_title = description_title;
    }

    public String getDescription_detail() {
        return description_detail;
    }

    public void setDescription_detail(String description_detail) {
        this.description_detail = description_detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Authors getAuthors() {
        return authors;
    }

    public void setAuthors(Authors authors) {
        this.authors = authors;
    }

    public Set<Books_Details> getBooksDetails() {
        return booksDetails;
    }

    public void setBooksDetails(Set<Books_Details> booksDetails) {
        this.booksDetails = booksDetails;
    }

    public Set<Books_Genres> getBooksGenres() {
        return booksGenres;
    }

    public void setBooksGenres(Set<Books_Genres> booksGenres) {
        this.booksGenres = booksGenres;
    }

    public Set<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Reviews> reviews) {
        this.reviews = reviews;
    }

    public Integer getTotalFormat() {
        return totalFormat;
    }

    public void setTotalFormat(Integer totalFormat) {
        this.totalFormat = totalFormat;
    }

    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }

    public Integer getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(Integer totalStock) {
        this.totalStock = totalStock;
    }

    public Integer getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(Integer totalSold) {
        this.totalSold = totalSold;
    }
}
