package bookshop.entities;

import javax.persistence.*;

@Entity
@Table(name = "Wishlist")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "User_Id", referencedColumnName = "Id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "Detail_Book_Id", referencedColumnName = "Id")
    private Books_Details booksDetails;

    public Wishlist() {
    }

    public Wishlist(Integer id, User user, Books_Details booksDetails) {
        this.id = id;
        this.user = user;
        this.booksDetails = booksDetails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Books_Details getBooksDetails() {
        return booksDetails;
    }

    public void setBooksDetails(Books_Details booksDetails) {
        this.booksDetails = booksDetails;
    }
}
