package bookshop.entities;

import javax.persistence.*;

@Entity
@Table(name = "Books_Genres")
public class Books_Genres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Books_Id", referencedColumnName = "Id")
    private Books books;

    @ManyToOne
    @JoinColumn(name = "Genres_Id", referencedColumnName = "Id")
    private Genres genres;

    public Books_Genres(){

    }

    public Books_Genres(Integer id, Books books, Genres genres) {
        this.id = id;
        this.books = books;
        this.genres = genres;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }

    public Genres getGenres() {
        return genres;
    }

    public void setGenres(Genres genres) {
        this.genres = genres;
    }
}
