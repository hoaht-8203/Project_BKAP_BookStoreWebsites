package bookshop.controller;

import bookshop.dao.*;
import bookshop.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private WishlistDAO wishlistDAO;
	@Autowired
	private Books_DetailsDAO books_detailsDAO;
	@Autowired
	private OrdersDAO ordersDAO;
	@Autowired
	private Orders_DetailsDAO ordersDetailsDAO;
	@Autowired
	private Books_GenresDAO booksGenresDAO;

	@RequestMapping("/home")
	public String homeUser(Model model){
		return "redirect:/home";
	}

	@RequestMapping("/viewAllNewBook")
	public String viewAllNewBookUser(){
		return "redirect:/viewAllNewBook";
	}

	@RequestMapping("/viewAllBestSell")
	public String viewAllBestSellUser(){
		return "redirect:/viewAllBestSell";
	}

	@RequestMapping("/viewAllOnSaleBook")
	public String viewAllOnSaleBookUser(){
		return "redirect:/viewAllOnSaleBook";
	}

	@RequestMapping("/viewAllGenreBook")
	public String viewAllGenreBookUser(@RequestParam("id")Integer id){
		return "redirect:/viewAllGenreBook?id="+id;
	}

	@RequestMapping("/viewMyWishlist")
	public String viewMyWishlist(Model model){
		CustomUserDetails user =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User byUserName = userDAO.findByUserName(user.getUsername());

		List<Wishlist> wishlists = wishlistDAO.loadAllWishlistByUserId(byUserName.getId());
		model.addAttribute("wishlists", wishlists);

		if (wishlists.size() == 0){
			return "redirect:/home";
		}

		return "front-end/wishlist";
	}

	@RequestMapping("/viewMyAccount")
	public String viewMyAccount(Model model){
		CustomUserDetails user =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User byUserName = userDAO.findByUserName(user.getUsername());

		model.addAttribute("user", byUserName);

		return "front-end/viewAccount";
	}

	@RequestMapping("/updatePasswordUser")
	public String updatePasswordUser(@RequestParam("oldPassword")String oldPassword,
									 @RequestParam("newPassword")String newPassword,
									 @RequestParam("confirmPassword")String confirmPassword,
									 Model model){
		CustomUserDetails userDetails =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


		if (oldPassword.length() == 0 || newPassword.length() == 0 || confirmPassword.length() == 0){
			model.addAttribute("failed", "you need to fill in all the blanks!");
		}else {
			User user = userDAO.findByUserName(userDetails.getUsername());

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			boolean check = BCrypt.checkpw(oldPassword, user.getPassWord());

			if (!check){
				model.addAttribute("failed", "Your old password is not correct, Try again!");
			}else {
				if (!newPassword.equals(confirmPassword)){
					model.addAttribute("failed", "Password Confirmation doesn't match Password, Try again!");

				}else {
					String encode = passwordEncoder.encode(confirmPassword);
					user.setPassWord(encode);

					boolean b = userDAO.updateUser(user);
					if (b){
						model.addAttribute("success", "Update password successfully!");
					}else {
						System.out.println("ERROR");
						model.addAttribute("failed", "Update password failed!");
					}
				}
			}
		}
		User byUserName = userDAO.findByUserName(userDetails.getUsername());
		model.addAttribute("user", byUserName);
		return "front-end/viewAccount";
	}

	@RequestMapping("/preUpdateAccountUser")
	public String preUpdateAccountUser(Model model){
		CustomUserDetails userDetails =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDAO.findByUserName(userDetails.getUsername());

		model.addAttribute("user", user);
		return "front-end/updateAccountUser";
	}

	@RequestMapping("/updateAccountUser")
	public String updateAccountUser(@Valid@ModelAttribute("user")User user,
									BindingResult result,
									Model model){
		if (result.hasErrors()){
			model.addAttribute("user", user);
			return "front-end/updateAccountUser";
		}else {
			boolean b = userDAO.updateUser(user);

			if (b){
				CustomUserDetails user1 =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				User byUserName = userDAO.findByUserName(user1.getUsername());

				model.addAttribute("user", byUserName);
				model.addAttribute("success", "Update successfully!");

				return "front-end/viewAccount";
			}else{
				model.addAttribute("user", user);
				return "front-end/updateAccountUser";
			}
		}
	}

	@RequestMapping("/viewCart")
	public String viewCartUser(){

		return "redirect:/viewCart";
	}

	@RequestMapping("/detailBook")
	public String detailBookUser(@RequestParam("id")Integer id){

		return "redirect:/detailBook?id="+id;
	}

	@RequestMapping("/addNewWishlist")
	public String addNewWishlist(@RequestParam("bookDetailId")Integer bookDetailId, Model model) {
		Books_Details booksDetailsById = books_detailsDAO.getBooksDetailsById(bookDetailId);
		CustomUserDetails user =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User byUserName = userDAO.findByUserName(user.getUsername());

		List<Wishlist> wishlists = wishlistDAO.loadAllWishlistByUserId(byUserName.getId());
		for (Wishlist w : wishlists){
			if (w.getBooksDetails().getId() == bookDetailId){
				return "redirect:/user/viewMyWishlist";
			}
		}

		Wishlist wishlist = new Wishlist();
		wishlist.setUser(byUserName);
		wishlist.setBooksDetails(booksDetailsById);

		Wishlist wishlistByUserIdAndBookId = wishlistDAO.getWishlistByUserIdAndBookId(byUserName.getId(), booksDetailsById.getId());
		if (wishlistByUserIdAndBookId != null){
			return "redirect:/user/viewMyWishlist";
		}

		boolean b = wishlistDAO.addNewWishlist(wishlist);

		if (!b){
			System.out.println("ERROR");
		}

		List<Wishlist> wishlists2 = wishlistDAO.loadAllWishlistByUserId(byUserName.getId());
		model.addAttribute("wishlists", wishlists2);

		return "front-end/wishlist";
	}

	@RequestMapping("/removeBookWishlist")
	public String removeBookWishlist(@RequestParam("id")Integer id, Model model){
		CustomUserDetails user =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User byUserName = userDAO.findByUserName(user.getUsername());

		Wishlist wishListByIdAndUserId = wishlistDAO.getWishListByIdAndUserId(id, byUserName.getId());
		boolean b = wishlistDAO.deleteWishlist(wishListByIdAndUserId);

		if (!b) {
			System.out.println("ERROR");
		}

		return "redirect:/user/viewMyWishlist";
	}

	@RequestMapping("/proceedToCheckout")
	public String proceedToCheckout(Model model,
									HttpSession session){
		Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

		if (cart == null){
			return "redirect:/home";
		}

		CustomUserDetails userDetails =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDAO.findByUserName(userDetails.getUsername());

		Orders orders = new Orders();
		orders.setFullName(user.getFullName());
		orders.setEmail(user.getEmail());
		orders.setPhoneNumber(user.getTelephone());
		orders.setAddress(user.getAddress());
		System.out.println(orders.getAddress() + " " + orders.getEmail() + " " + orders.getFullName());
		model.addAttribute("order", orders);

		getListCartSession(model, session, cart);

		return "front-end/checkout";
	}

	@RequestMapping("/addNewOrder")
	public String addNewOrder(@Valid@ModelAttribute("order")Orders orders,
							  BindingResult result,
							  Model model,
							  HttpSession session){
		if (result.hasErrors()){
			Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

			if (cart == null){
				return "redirect:/home";
			}

			getListCartSession(model, session, cart);

			model.addAttribute("order", orders);

			return "front-end/checkout";
		}else {
			Date currentDate = new Date();
			// Format the date as yyyy-MM-dd
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = formatter.format(currentDate);

			// Set the formatted date to start_date
			try {
				orders.setOrderDate(formatter.parse(formattedDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			orders.setStatus(0);
			orders.setTypeShip("COD");

			boolean b = ordersDAO.addNewOrder(orders);

			Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

			if (b){
				System.out.println("NOT ERROR");
				for (Map.Entry<Integer, Integer> entry : cart.entrySet()){
					Orders_Details ordersDetails = new Orders_Details();
					ordersDetails.setOrders(orders);

					Books_Details booksDetailsById = books_detailsDAO.getBooksDetailsById(entry.getKey());
					Float priceAfterSale = (float) 0;
					if (booksDetailsById.getBooks().getSales()!=null){
						priceAfterSale = booksDetailsById.getPrice()*(1-booksDetailsById.getBooks().getSales().getPercentage()/100);
					}else {
						priceAfterSale = booksDetailsById.getPrice();
					}

					Float totalPrice = priceAfterSale*entry.getValue();

					ordersDetails.setBooksDetails(booksDetailsById);
					ordersDetails.setQuantity(entry.getValue());
					ordersDetails.setTotalPrice(totalPrice);


					boolean b1 = ordersDetailsDAO.addNewOrderDetail(ordersDetails);

					if (!b1) {
						System.out.println("ERROR 1");
					}
				}

				return "redirect:/home";
			}else {
				System.out.println("ERROR 2");
				if (cart == null){
					return "redirect:/home";
				}

				getListCartSession(model, session, cart);

				model.addAttribute("order", orders);

				return "front-end/checkout";
			}
		}
	}

	public void getListCartSession(Model model, HttpSession session, Map<Integer, Integer> cart){
		ArrayList<Books_Details_Cart> booksDetails = new ArrayList<>();

		for (Map.Entry<Integer, Integer> entry : cart.entrySet()){
			Books_Details booksDetailsById = books_detailsDAO.getBooksDetailsById(entry.getKey());

			Float priceAfterSale = (float) 0;
			if (booksDetailsById.getBooks().getSales()!=null){
				priceAfterSale = booksDetailsById.getPrice()*(1-booksDetailsById.getBooks().getSales().getPercentage()/100);
			}else {
				priceAfterSale = booksDetailsById.getPrice();
			}

			Float totalPrice = priceAfterSale*entry.getValue();

			Books_Details_Cart booksDetailsCart = new Books_Details_Cart(booksDetailsById.getId(),
					booksDetailsById.getBooks().getImage(), booksDetailsById.getBooks().getTitle(),
					priceAfterSale, entry.getValue(), totalPrice);

			booksDetails.add(booksDetailsCart);
		}

		Float subTotal = (float) 0;
		for (Books_Details_Cart bdc : booksDetails){
			subTotal += bdc.getTotalPrice();
		}

		model.addAttribute("subTotal", subTotal);

		getTotalInCart(model, cart);

		model.addAttribute("listBook", booksDetails);
	}

	public void getTotalInCart(Model model, Map<Integer, Integer> cart){
		int totalQuantity = 0;

		for (int quantity : cart.values()) {
			totalQuantity += quantity;
		}

		model.addAttribute("totalCartBook", totalQuantity);
	}

	@RequestMapping("/addBookToCart")
	public String addBookToCart(@RequestParam("bookDetailId")Integer bookDetailId,
								Model model,
								HttpSession session){

		return "redirect:/addBookToCart?bookDetailId="+bookDetailId;
	}
}
