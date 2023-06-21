package bookshop.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "Formats")
public class Formats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @NotEmpty(message = "Formats name is cannot empty!")
    @Column(name = "Name")
    private String name;
    @Column(name = "TotalBook")
    private Integer totalBook;
    @OneToMany(mappedBy = "formats")
    private Set<Books_Details> booksDetails;

    public Formats(){

    }

    public Formats(Integer id, String name, Integer totalBook, Set<Books_Details> booksDetails) {
        this.id = id;
        this.name = name;
        this.totalBook = totalBook;
        this.booksDetails = booksDetails;
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

    public Integer getTotalBook() {
        return totalBook;
    }

    public void setTotalBook(Integer totalBook) {
        this.totalBook = totalBook;
    }

    public Set<Books_Details> getBooksDetails() {
        return booksDetails;
    }

    public void setBooksDetails(Set<Books_Details> booksDetails) {
        this.booksDetails = booksDetails;
    }
}
