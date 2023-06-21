package bookshop.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "Genres")
public class Genres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @NotEmpty(message = "Genre name is cannot empty!")
    @Column(name = "name")
    private String name;
    @Column(name = "TotalBook")
    private Integer totalBook;

    @OneToMany(mappedBy = "genres")
    private Set<Books_Genres> booksGenres;

    public Genres(){

    }

    public Genres(Integer id, String name, Integer totalBook, Set<Books_Genres> booksGenres) {
        this.id = id;
        this.name = name;
        this.totalBook = totalBook;
        this.booksGenres = booksGenres;
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

    public Set<Books_Genres> getBooksGenres() {
        return booksGenres;
    }

    public void setBooksGenres(Set<Books_Genres> booksGenres) {
        this.booksGenres = booksGenres;
    }
}
