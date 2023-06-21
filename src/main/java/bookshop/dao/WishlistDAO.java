package bookshop.dao;

import bookshop.entities.Wishlist;

import java.util.List;

public interface WishlistDAO {
    public boolean addNewWishlist(Wishlist wishlist);
    public List<Wishlist> loadAllWishlistByUserId(Long userId);
    public Wishlist getWishlistById(Integer id);
    public Wishlist getWishListByIdAndUserId(Integer id, Long userId);
    public boolean deleteWishlistById(Integer id);
    public boolean deleteWishlist(Wishlist wishlist);
    public Wishlist getWishlistByUserIdAndBookId(Long userId, Integer bookId);
}
