package bookshop.entities;

import javax.persistence.*;

@Entity
@Table(name = "Reviews")
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "References")
    private String references;
    @Column(name = "Content")
    private String content;

    @ManyToOne()
    @JoinColumn(name = "Books_Id", referencedColumnName = "Id")
    private Books books;

    public Reviews(){

    }

    public Reviews(Integer id, String references, String content, Books books) {
        this.id = id;
        this.references = references;
        this.content = content;
        this.books = books;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }
}
