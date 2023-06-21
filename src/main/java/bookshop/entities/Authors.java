package bookshop.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "Authors")
public class Authors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @NotEmpty(message = "Name of author is cannot empty!")
    @Column(name = "Full_name")
    private String fullName;
    @NotEmpty(message = "Description about the author is cannot empty!")
    @Column(name = "Description")
    private String description;
    @Column(name = "TotalBook")
    private Integer totalBook;

    @OneToMany(mappedBy = "authors")
    private Set<Books> books;

    public Authors(){

    }

    public Authors(Integer id, String fullName, String description, Set<Books> books, Integer totalBook) {
        this.id = id;
        this.fullName = fullName;
        this.description = description;
        this.books = books;
        this.totalBook = totalBook;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Books> getBooks() {
        return books;
    }

    public void setBooks(Set<Books> books) {
        this.books = books;
    }

    public Integer getTotalBook() {
        return totalBook;
    }

    public void setTotalBook(Integer totalBook) {
        this.totalBook = totalBook;
    }
}
