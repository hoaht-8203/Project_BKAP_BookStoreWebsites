package bookshop.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import bookshop.dao.*;
import bookshop.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AppController {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private BooksDAO booksDAO;
	@Autowired
	private Books_DetailsDAO books_detailsDAO;
	@Autowired
	private User_RoleDAO user_RoleDAO;
	@Autowired
	private Books_GenresDAO booksGenresDAO;
	@Autowired
	private SalesDAO salesDAO;
	@Autowired
	private OrdersDAO ordersDAO;
	@Autowired
	private Orders_DetailsDAO ordersDetailsDAO;
	@Autowired
	private AuthorDAO authorDAO;

	public List<Books_Details> getUniqueListNewBook(List<Books> booksList){
		List<Integer> listInt = new ArrayList<>();

		for (Books b : booksList){
			listInt.add(b.getId());
		}

		Set<Integer> uniqueListInt = new HashSet<>(listInt);

		List<IntegerList> bookFormatId = new ArrayList<>();

		for (Integer i : uniqueListInt){
			List<Books_Details> formatsByBookId = books_detailsDAO.getFormatsByBookId(i);

			Books_Details booksDetails = Collections.min(formatsByBookId, Comparator.comparingInt(bd -> bd.getFormats().getId()));

			bookFormatId.add(new IntegerList(booksDetails.getId(), booksDetails.getBooks().getId(), booksDetails.getFormats().getId()));
		}

		List<Books_Details> list = new ArrayList<>();

		for (IntegerList i : bookFormatId){
			Books_Details booksDetailsById = books_detailsDAO.getBooksDetailsById(i.getBookDetailId());
			list.add(booksDetailsById);
		}

		return list;
	}
	public List<Books_Details> getUniqueListBestSell(List<Books_Details> bestSell){
		List<Integer> listInt = new ArrayList<>();

		for (Books_Details bd : bestSell){
			listInt.add(bd.getBooks().getId());
		}

		Set<Integer> uniqueListInt = new HashSet<>(listInt);

		List<IntegerList> bookFormatId = new ArrayList<>();

		for (Integer i : uniqueListInt){
			List<Books_Details> formatsByBookId = books_detailsDAO.getFormatsByBookId(i);

			Books_Details booksDetails = Collections.min(formatsByBookId, Comparator.comparingInt(bd -> bd.getFormats().getId()));

			bookFormatId.add(new IntegerList(booksDetails.getId(), booksDetails.getBooks().getId(), booksDetails.getFormats().getId()));
		}

		List<Books_Details> list = new ArrayList<>();

		for (IntegerList i : bookFormatId){
			Books_Details booksDetailsById = books_detailsDAO.getBooksDetailsById(i.getBookDetailId());
			list.add(booksDetailsById);
		}

		return list;
	}
	public List<Books_Details> getIndexBoonDetail(List<Books_Details> list, int amount){
		List<Books_Details> unique4ListNewBook = new ArrayList<>();
		int count = 0;
		for (Books_Details bd : list){
			unique4ListNewBook.add(bd);
			count++;

			if (count == amount){
				break;
			}
		}
		return unique4ListNewBook;
	}

	public void updateSalesStatus(){
		LocalDate currentDate = LocalDate.now();
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		Date today = null;
		try {
			today = sf.parse(currentDate.getDayOfMonth()+"/"+currentDate.getMonthValue()+"/"+currentDate.getYear());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		List<Sales> salesList = salesDAO.loadAllSales();
		for (Sales s : salesList){
			if (s.getStart_date().after(today)){
				s.setStatus(false);
			}else if (s.getEnd_date().before(today)){
				s.setStatus(false);
			}else {
				s.setStatus(true);
			}

			boolean b = salesDAO.updateSales(s);

			if (!b){
				System.out.println("ERROR");
			}
		}
	}

	public void updatePriceSale(){
		List<Books_Details> booksDetailsList = books_detailsDAO.loadAllBookDetail();
		for (Books_Details bd : booksDetailsList){
			if (bd.getBooks().getSales() != null && bd.getBooks().getSales().getStatus()){
				float priceAfterSale = bd.getPrice()*(1-bd.getBooks().getSales().getPercentage()/100);
				float priceAfterSaleRound = Math.round(priceAfterSale * 100) / 100f;

				bd.setPriceAfterSale(priceAfterSaleRound);

				boolean b = books_detailsDAO.updateBookDetail(bd);

				if (!b){
					System.out.println("ERROR");
				}
			}else{
				float priceAfterSale = bd.getPrice();
				bd.setPriceAfterSale(priceAfterSale);
				Books detailsBookById = booksDAO.getDetailsBookById(bd.getBooks().getId());
				detailsBookById.setSales(null);

				boolean b1 = booksDAO.updateBook(detailsBookById);
				boolean b = books_detailsDAO.updateBookDetail(bd);

				if (!b || !b1){
					System.out.println("ERROR");
				}
			}
		}
	}


	@RequestMapping(value = {"/","/home"})
	public String home(Model model, HttpSession session) {
		boolean isLogin = false;
		model.addAttribute("isLogin", isLogin);

		updateSalesStatus();
		updatePriceSale();

		List<Books> booksList = booksDAO.loadNewestBookByMonth(1);
		List<Books_Details> uniqueListNewBook = getUniqueListNewBook(booksList);
		List<Books_Details> listNewBook4 = getIndexBoonDetail(uniqueListNewBook, 4);

		List<Books_Details> bestSell = books_detailsDAO.loadListBestSell(25);
		List<Books_Details> uniqueListBestSell = getUniqueListBestSell(bestSell);
		List<Books_Details> listBestSell4 = getIndexBoonDetail(uniqueListBestSell, 4);

		List<Books> onSaleList = booksDAO.loadOnSaleBook();
		List<Books_Details> uniqueOnSaleList = getUniqueListNewBook(onSaleList);
		List<Books_Details> listOnSaleBook = getIndexBoonDetail(uniqueOnSaleList, 6);

		List<Sales> sales = salesDAO.loadAllSales();
		model.addAttribute("salesImg", sales);
		model.addAttribute("saleSize", sales.size()-1);
		Sales firstSale = sales.get(sales.size()-1);
		model.addAttribute("firstSale", firstSale.getId());

		model.addAttribute("listNewBook", listNewBook4);
		model.addAttribute("countNewBook", uniqueListNewBook.size());

		model.addAttribute("listBookBestSell", listBestSell4);
		model.addAttribute("countBestSell", uniqueListBestSell.size());

		model.addAttribute("listOnSaleBook", listOnSaleBook);
		model.addAttribute("countOnSaleBook", uniqueOnSaleList.size());

		model.addAttribute("home", "active");

		countBookInCart(model, session);

		return "front-end/home";
	}

	@RequestMapping("/filterCategory")
	public String filterCategory(@RequestParam("filter")String filterName,
								 @RequestParam("pageName")String pageName,
								 Model model){
		if (filterName.equals("none")){
			if (pageName.equals("viewAllNewBook")){
				return "redirect:/viewAllNewBook";
			} else if (pageName.equals("viewAllBestSell")) {
				return "redirect:/viewAllBestSell";
			}else if (pageName.equals("viewAllOnSaleBook")){
				return "redirect:/viewAllOnSaleBook";
			}
		}else if (filterName.equals("priceIncrease")){
			if (pageName.equals("viewAllNewBook")){
				return "redirect:/viewAllNewBook?isFilter=1";
			}else if (pageName.equals("viewAllBestSell")){
				return "redirect:/viewAllBestSell?isFilter=1";
			} else if (pageName.equals("viewAllOnSaleBook")) {
				return "redirect:/viewAllOnSaleBook?isFilter=1";
			}
		}else if (filterName.equals("priceDecrease")){
			if (pageName.equals("viewAllNewBook")){
				return "redirect:/viewAllNewBook?isFilter=2";
			}else if (pageName.equals("viewAllBestSell")){
				return "redirect:/viewAllBestSell?isFilter=2";
			}else if (pageName.equals("viewAllOnSaleBook")){
				return "redirect:/viewAllOnSaleBook?isFilter=2";
			}
		}
		return "redirect:/home";
	}

	@RequestMapping("/viewAllBookSale")
	public String viewAllBookSale(@RequestParam("id")Integer saleId,
								  Model model,
								  HttpSession session){
		List<Books> booksList = booksDAO.getBookBySaleId(saleId);
		List<Books_Details> uniqueListNewBook = getUniqueListNewBook(booksList);

		model.addAttribute("listBook", uniqueListNewBook);
		countBookInCart(model, session);

		return "front-end/category";
	}

	public void sortNewBookPriceIncrease(Model model, HttpSession session){
		Set<Integer> bookDetailId = new HashSet<>();
		List<Books_Details> bookDetailUnique = new ArrayList<>();

		List<Books_Details> booksDetailsList = books_detailsDAO.loadListNewBookOrderPriceAsc( 1);

		for (Books_Details bd : booksDetailsList){
			if (!bookDetailId.contains(bd.getBooks().getId())){
				bookDetailId.add(bd.getBooks().getId());
				bookDetailUnique.add(bd);
			}
		}

		model.addAttribute("listBook", bookDetailUnique);

		model.addAttribute("newBookActive", "Inactive");
		model.addAttribute("categoryName", "New Books");
		model.addAttribute("newBookActive", "active");

		countBookInCart(model, session);

		model.addAttribute("pageName", "viewAllNewBook");
	}
	public void sortNewBookPriceDecrease(Model model, HttpSession session){
		Set<Integer> bookDetailId = new HashSet<>();
		List<Books_Details> bookDetailUnique = new ArrayList<>();

		List<Books_Details> booksDetailsList = books_detailsDAO.loadListNewBookOrderPriceDcs(1);

		for (Books_Details bd : booksDetailsList){
			if (!bookDetailId.contains(bd.getBooks().getId())){
				bookDetailId.add(bd.getBooks().getId());
				bookDetailUnique.add(bd);
			}
		}

		model.addAttribute("listBook", bookDetailUnique);

		model.addAttribute("newBookActive", "Inactive");
		model.addAttribute("categoryName", "New Books");
		model.addAttribute("newBookActive", "active");

		countBookInCart(model, session);

		model.addAttribute("pageName", "viewAllNewBook");
	}

	@RequestMapping("/viewAllNewBook")
	public String viewAllNewBook(@RequestParam(name = "page", required = false)Integer page,
								 @RequestParam(name = "isFilter", required = false)Integer isFilter,
								 Model model,
								 HttpSession session){
		if (page == null){
			page = 1;
		}

		if (isFilter == null){
			isFilter = 0;
		}

		int maxResult = 12;

		int offset = (page-1)*maxResult;

		long totalBook = booksDAO.getTotalNewestBookByMonth(1);
		int totalPage = (int) (totalBook/maxResult+(totalBook%maxResult==0?0:1));

		Map<Integer, Integer> listPage = new HashMap<>();
		for (int i = 1; i <= totalPage; i++){
			listPage.put(i, isFilter);
		}
		model.addAttribute("listPage", listPage);


		if (isFilter == 1){
			sortNewBookPriceIncrease(model, session);

			return "front-end/category";
		}else if (isFilter == 2){
			sortNewBookPriceDecrease(model, session);

			return "front-end/category";
		}

		List<Books> booksList = booksDAO.loadNewestBookByMonthPaging(1, offset, maxResult);
		List<Books_Details> uniqueListNewBook = getUniqueListNewBook(booksList);

		model.addAttribute("listBook", uniqueListNewBook);

		model.addAttribute("newBookActive", "active");
		model.addAttribute("categoryName", "New Books");

		countBookInCart(model, session);

		model.addAttribute("pageName", "viewAllNewBook");

		return "front-end/category";
	}

	public void sortBestSellBookPriceIncrease(Model model, HttpSession session){
		Set<Integer> bookDetailId = new HashSet<>();
		List<Books_Details> bookDetailUnique = new ArrayList<>();

		List<Books_Details> booksDetailsList = books_detailsDAO.getBesSellerBookPagingSortAsc(25);

		for (Books_Details bd : booksDetailsList){
			if (!bookDetailId.contains(bd.getBooks().getId())){
				bookDetailId.add(bd.getBooks().getId());
				bookDetailUnique.add(bd);
			}
		}

		model.addAttribute("listBook", bookDetailUnique);

		model.addAttribute("onSaleActive", "Inactive");
		model.addAttribute("categoryName", "Best Selling Books");
		model.addAttribute("pageName", "viewAllBestSell");
		model.addAttribute("bestSellerActive", "active");

		countBookInCart(model, session);
	}
	public void sortBestSellBookPriceDecrease(Model model, HttpSession session){
		Set<Integer> bookDetailId = new HashSet<>();
		List<Books_Details> bookDetailUnique = new ArrayList<>();

		List<Books_Details> booksDetailsList = books_detailsDAO.getBesSellerBookPagingSortDcs(25);

		for (Books_Details bd : booksDetailsList){
			if (!bookDetailId.contains(bd.getBooks().getId())){
				bookDetailId.add(bd.getBooks().getId());
				bookDetailUnique.add(bd);
			}
		}

		model.addAttribute("listBook", bookDetailUnique);

		model.addAttribute("onSaleActive", "Inactive");
		model.addAttribute("categoryName", "Best Selling Books");
		model.addAttribute("pageName", "viewAllBestSell");
		model.addAttribute("bestSellerActive", "active");

		countBookInCart(model, session);
	}

	@RequestMapping("/viewAllBestSell")
	public String viewAllBestSell(@RequestParam(name = "page", required = false)Integer page,
								  @RequestParam(name = "isFilter", required = false)Integer isFilter,
								  Model model, HttpSession session){
		if (page == null){
			page = 1;
		}

		if (isFilter == null){
			isFilter = 0;
		}

		int maxResult = 12;
		int offset = (page-1)*maxResult;

		Long totalBook = booksDAO.getTotalBestSellBook(25);
		int totalPage = (int) (totalBook/maxResult+(totalBook%maxResult==0?0:1));

		Map<Integer, Integer> listPage = new HashMap<>();
		for (int i = 1; i <= totalPage; i++){
			listPage.put(i, isFilter);
		}
		model.addAttribute("listPage", listPage);

		if (isFilter == 1){
			sortBestSellBookPriceIncrease(model, session);

			return "front-end/category";
		} else if (isFilter == 2) {
			sortBestSellBookPriceDecrease(model, session);

			return "front-end/category";
		}

		List<Books> besSellerBookPaging = booksDAO.getBesSellerBookPaging(25, offset, maxResult);
		List<Books_Details> uniqueListBestSell = getUniqueListNewBook(besSellerBookPaging);

		model.addAttribute("listBook", uniqueListBestSell);

		model.addAttribute("bestSellerActive", "active");
		model.addAttribute("categoryName", "Best Selling Books");
		model.addAttribute("pageName", "viewAllBestSell");

		countBookInCart(model, session);

		return "front-end/category";
	}

	public void sortOnSaleBookPriceDecrease(Model model, HttpSession session){
		Set<Integer> bookDetailId = new HashSet<>();
		List<Books_Details> bookDetailUnique = new ArrayList<>();

		List<Books_Details> booksDetailsList = books_detailsDAO.loadOnSaleBookPagingSortDcs();

		for (Books_Details bd : booksDetailsList){
			if (!bookDetailId.contains(bd.getBooks().getId())){
				bookDetailId.add(bd.getBooks().getId());
				bookDetailUnique.add(bd);
			}
		}

		model.addAttribute("listBook", bookDetailUnique);

		model.addAttribute("onSaleActive", "active");
		model.addAttribute("categoryName", "On Selling Books");
		model.addAttribute("pageName", "viewAllOnSaleBook");
		model.addAttribute("onSaleActive", "active");

		countBookInCart(model, session);
	}
	public void sortOnSaleBookPriceIncrease(Model model, HttpSession session){
		Set<Integer> bookDetailId = new HashSet<>();
		List<Books_Details> bookDetailUnique = new ArrayList<>();

		List<Books_Details> booksDetailsList = books_detailsDAO.loadOnSaleBookPagingSortAsc();

		for (Books_Details bd : booksDetailsList){
			if (!bookDetailId.contains(bd.getBooks().getId())){
				bookDetailId.add(bd.getBooks().getId());
				bookDetailUnique.add(bd);
			}
		}

		model.addAttribute("listBook", bookDetailUnique);

		model.addAttribute("onSaleActive", "active");
		model.addAttribute("categoryName", "On Selling Books");
		model.addAttribute("pageName", "viewAllOnSaleBook");
		model.addAttribute("onSaleActive", "active");

		countBookInCart(model, session);
	}


	@RequestMapping("/viewAllOnSaleBook")
	public String viewAllOnSaleBook(@RequestParam(name = "page", required = false)Integer page,
									@RequestParam(name = "isFilter", required = false)Integer isFilter,
									Model model, HttpSession session){
		if (page == null){
			page = 1;
		}

		if (isFilter == null){
			isFilter = 0;
		}

		int maxResult = 12;
		int offset = (page-1)*maxResult;

		Long totalBook = booksDAO.getTotalOnSaleBook();
		int totalPage = (int) (totalBook/maxResult+(totalBook%maxResult==0?0:1));

		List<Integer> listPage = new ArrayList<>();
		for (int i = 1; i <= totalPage; i++) {
			listPage.add(i);
		}
		model.addAttribute("listPage", listPage);

		if (isFilter == 1){
			sortOnSaleBookPriceIncrease(model, session);
			return "front-end/category";
		} else if (isFilter == 2) {
			sortOnSaleBookPriceDecrease(model, session);
			return "front-end/category";
		}

		List<Books> onSaleList = booksDAO.loadOnSaleBookPaging(offset, maxResult);
		List<Books_Details> uniqueOnSaleList = getUniqueListNewBook(onSaleList);

		model.addAttribute("listBook", uniqueOnSaleList);

		model.addAttribute("onSaleActive", "active");
		model.addAttribute("categoryName", "On Selling Books");
		model.addAttribute("pageName", "viewAllOnSaleBook");

		countBookInCart(model, session);

		return "front-end/category";
	}

	@RequestMapping("/detailBook")
	public String detailBook(@RequestParam("id")Integer id, Model model, HttpSession session){
		Books_Details booksDetailsById = books_detailsDAO.getBooksDetailsById(id);
		model.addAttribute("bookDetail", booksDetailsById);

		List<Books_Details> formatsByBookId = books_detailsDAO.getFormatsByBookId(booksDetailsById.getBooks().getId());
		model.addAttribute("listFormat", formatsByBookId);

		List<Books_Genres> booksGenres = booksGenresDAO.loadBooksGenresByBookId(booksDetailsById.getBooks().getId());
		model.addAttribute("bookGenre", booksGenres);

		countBookInCart(model, session);

		return "front-end/detail_books";
	}

	@RequestMapping("/viewAllGenreBook")
	public String viewAllGenreBook(@RequestParam("id")Integer id,
								   @RequestParam(name = "page", required = false)Integer page,
								   Model model,
								   HttpSession session){
		if (page == null){
			page = 1;
		}

		int maxResult = 12;
		int offset = (page-1)*maxResult;

		Long totalBook = booksGenresDAO.getTotalBookGenreByGenreId(id);
		int totalPage = (int) (totalBook/maxResult+(totalBook%maxResult==0?0:1));

		// genre (KIDS) have Id is 61
		ArrayList<Books_Details> list = new ArrayList<>();

		List<Books_Genres> booksGenres = booksGenresDAO.loadBookGenresByGenreIdPaging(id, offset, maxResult);
		for (Books_Genres bg : booksGenres){
			List<Books_Details> formatsByBookId = books_detailsDAO.getFormatsByBookId(bg.getBooks().getId());
			list.addAll(formatsByBookId);
		}

		List<Books_Details> uniqueKidBook = getUniqueListBestSell(list);
		model.addAttribute("listBook", uniqueKidBook);

		List<Integer> listPage = new ArrayList<>();
		for (int i = 1; i <= totalPage; i++) {
			listPage.add(i);
		}
		model.addAttribute("listPage", listPage);

		model.addAttribute("genreBookActive", "active");

		Books_Genres booksGenres1 = booksGenres.get(0);

		model.addAttribute("genre", booksGenres1);
		model.addAttribute("categoryName", booksGenres1.getGenres().getName() + " Books");
		model.addAttribute("pageName", "viewAllGenreBook");

		switch (id) {
			case 61 -> model.addAttribute("kidActive", "active");
			case 82 -> model.addAttribute("mysteriesActive", "active");
			case 83 -> model.addAttribute("horrorActive", "active");
			case 81 -> model.addAttribute("cookingActive", "active");
		}

		countBookInCart(model, session);

		return "front-end/category";
	}

	@RequestMapping("/viewAllBookByAuthorId")
	public String viewAllBookByAuthorId(@RequestParam("authorId")Integer authorId,
										@RequestParam(name = "page", required = false)Integer page,
										Model model,
										HttpSession session){
		if (page == null){
			page = 1;
		}

		int maxResult = 12;
		int offset = (page-1)*maxResult;

		Long totalBook = booksDAO.getTotalBookByAuthorId(authorId);
		int totalPage = (int) (totalBook/maxResult+(totalBook%maxResult==0?0:1));

		List<Books> authorBook = booksDAO.loadBookByAuthorIdPaging(authorId, offset, maxResult);
		List<Books_Details> uniqueAuthorBook = getUniqueListNewBook(authorBook);

		List<Integer> listPage = new ArrayList<>();
		for (int i = 1; i <= totalPage; i++) {
			listPage.add(i);
		}

		model.addAttribute("listPage", listPage);
		model.addAttribute("listBook", uniqueAuthorBook);
		model.addAttribute("authorBookActive", "active");

		Books books = authorBook.get(0);

		model.addAttribute("categoryName", books.getAuthors().getFullName() + " Books");

		model.addAttribute("pageName", books.getAuthors().getFullName());

		countBookInCart(model, session);

		return "front-end/category";
	}

	public void countBookInCart(Model model, HttpSession session){
		Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

		if (cart == null){
			cart = new HashMap<>();
		}

		int totalQuantity = 0;

		for (int quantity : cart.values()) {
			totalQuantity += quantity;
		}

		model.addAttribute("totalCartBook", totalQuantity);
	}

	@RequestMapping("/searchBook")
	public String searchBook(@RequestParam(name = "page", required = false)Integer page,
							 @RequestParam("searchText")String searchText, Model model,
							 HttpSession session){
		if (page == null){
			page = 1;
		}

		int maxResult = 12;
		int offset = (page-1)*maxResult;

		Long totalBook = booksDAO.totalBookFindBookByTitleOrAuthorNamePaging(searchText);
		int totalPage = (int) (totalBook/maxResult+(totalBook%maxResult==0?0:1));

		List<Books> bookSearch = booksDAO.findBookByTitleOrAuthorNamePaging(searchText, offset, maxResult);

		if (bookSearch.size()==0){
			Long totalBook2 = booksGenresDAO.getTotalBookGenreByName(searchText);
			int totalPage2 = (int) (totalBook2/maxResult+(totalBook2%maxResult==0?0:1));

			List<Books_Genres> bookGenreByName = booksGenresDAO.findBookGenreByNamePaging(searchText, offset, maxResult);
			List<Books> booksList = new ArrayList<>();
			for (Books_Genres bg : bookGenreByName){
				booksList.add(bg.getBooks());
			}

			List<Books_Details> uniqueListNewBook = getUniqueListNewBook(booksList);
			model.addAttribute("listBook", uniqueListNewBook);

			countBookInCart(model, session);

			List<Integer> listPage2 = new ArrayList<>();
			for (int i = 1; i <= totalPage2; i++) {
				listPage2.add(i);
			}
			model.addAttribute("listPage", listPage2);
			model.addAttribute("findBookActive", "active");
			model.addAttribute("searchText", searchText);

			model.addAttribute("searchActive", "active");
			model.addAttribute("result", uniqueListNewBook.size());

			return "front-end/category";
		}

		List<Books_Details> uniqueListNewBook = getUniqueListNewBook(bookSearch);

		model.addAttribute("listBook", uniqueListNewBook);

		List<Integer> listPage = new ArrayList<>();
		for (int i = 1; i <= totalPage; i++) {
			listPage.add(i);
		}
		model.addAttribute("listPage", listPage);
		model.addAttribute("result", uniqueListNewBook.size());

		model.addAttribute("findBookActive", "active");
		model.addAttribute("searchText", searchText);

		model.addAttribute("genre", "something");
		model.addAttribute("searchActive", "active");
		countBookInCart(model, session);

		return "front-end/category";
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
		Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

		if (cart == null){
			cart = new HashMap<>();
		}

		getTotalInCart(model, cart);

		if (cart.containsKey(bookDetailId)){
			int quantity = cart.get(bookDetailId);
			cart.put(bookDetailId, quantity+1);
		}else {
			cart.put(bookDetailId, 1);
		}

		session.setAttribute("cart", cart);

		Books_Details booksDetailsById = books_detailsDAO.getBooksDetailsById(bookDetailId);
		model.addAttribute("bookDetail", booksDetailsById);

		List<Books_Details> formatsByBookId = books_detailsDAO.getFormatsByBookId(booksDetailsById.getBooks().getId());
		model.addAttribute("listFormat", formatsByBookId);

		List<Books_Genres> booksGenres = booksGenresDAO.loadBooksGenresByBookId(booksDetailsById.getBooks().getId());
		model.addAttribute("bookGenre", booksGenres);

		return "front-end/detail_books";
	}

	@RequestMapping("/viewCart")
	public String viewCart(Model model, HttpSession session){
		Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

		if (cart == null){
			return "redirect:/home";
		}

		ArrayList<Books_Details_Cart> booksDetails = new ArrayList<>();

		for (Map.Entry<Integer, Integer> entry : cart.entrySet()){
			Books_Details booksDetailsById = books_detailsDAO.getBooksDetailsById(entry.getKey());

			Float priceAfterSale = (float) 0;
			if (booksDetailsById.getBooks().getSales()!=null){
				priceAfterSale = booksDetailsById.getPriceAfterSale();
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

		return "front-end/cart";
	}


	@RequestMapping("/updateCart")
	public String updateCart(@RequestParam("bookDetailId")int[] bookDetailIds,
							 @RequestParam("quantity")int[] quantities,
							 Model model,
							 HttpSession session){
		Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
		for (int i = 0; i < bookDetailIds.length; i++){
			int bookDetailId = bookDetailIds[i];
			int quantity = quantities[i];
			if (quantity <= 0){
				quantity = 1;
			}

			if (cart.containsKey(bookDetailId)){
				cart.put(bookDetailId, quantity);
			}
		}

		session.setAttribute("cart", cart);

		return "redirect:/viewCart";
	}

	@RequestMapping("/removeCart")
	public String removeCart(@RequestParam("bookId")Integer bookId,
							 Model model,
							 HttpSession session){
		Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

		if (cart != null){
			cart.remove(bookId);

			if (cart.isEmpty()){
				session.removeAttribute("cart");

				return "redirect:/home";
			}
		}

		return "redirect:/viewCart";
	}

	public void getListCartSession(Model model, HttpSession session, Map<Integer, Integer> cart){
		ArrayList<Books_Details_Cart> booksDetails = new ArrayList<>();

		for (Map.Entry<Integer, Integer> entry : cart.entrySet()){
			Books_Details booksDetailsById = books_detailsDAO.getBooksDetailsById(entry.getKey());

			Float priceAfterSale = (float) 0;
			if (booksDetailsById.getBooks().getSales()!=null){
				priceAfterSale = booksDetailsById.getPriceAfterSale();
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

	@RequestMapping("/proceedToCheckout")
	public String proceedToCheckout(Model model,
									HttpSession session){
		Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

		if (cart == null){
			return "redirect:/home";
		}

		Orders orders = new Orders();
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
			orders.setTotalPriceOrder((float) 0);

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
						priceAfterSale = booksDetailsById.getPriceAfterSale();
					}else {
						priceAfterSale = booksDetailsById.getPrice();
					}

					Float totalPrice = priceAfterSale*entry.getValue();

					ordersDetails.setBooksDetails(booksDetailsById);
					ordersDetails.setQuantity(entry.getValue());
					ordersDetails.setTotalPrice(totalPrice);

					boolean b1 = ordersDetailsDAO.addNewOrderDetail(ordersDetails);

					List<Orders_Details> ordersDetailsList = ordersDetailsDAO.loadOrdersDetailsByOrderId(orders.getId());
					for (Orders_Details od : ordersDetailsList){
						if (!od.getBooksDetails().getAvailable()){
							orders.setStatus(3);
						}
					}

					boolean b2 = ordersDAO.updateOrders(orders);

					if (!b1 || !b2) {
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

	@RequestMapping("/viewMyWishlist")
	public String viewMyWishlist(){
		return "redirect:/user/viewMyWishlist";
	}

	@RequestMapping("/viewMyAccount")
	public String viewMyAccount(){
		return "redirect:/user/viewMyAccount";
	}
	
	@RequestMapping("/preLogin")
	public String preLogin() {
		return "front-end/login";
	}
	
	@RequestMapping("/preRegistration")
	public String preRegistration(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "front-end/register";
	}
	
	@RequestMapping("/registerUser")
	public String registerUser(@Valid@ModelAttribute("user")User user,
							   BindingResult result,
							   @RequestParam("password")String password, @RequestParam("confirmPassword")String confirmPassword,
							   Model model) {
		if (user.getUserName() == null || user.getUserName().length() == 0){
			model.addAttribute("user", user);
			model.addAttribute("failedUserName", "Username is cannot empty!");
			return "front-end/register";
		}else if (user.getUserName().length() <= 10 && user.getUserName().length() > 0){
			model.addAttribute("user", user);
			model.addAttribute("failedUserName", "Username needs at least 10 characters!");
			return "front-end/register";
		}else if (user.getUserName().length() >= 25){
			model.addAttribute("user", user);
			model.addAttribute("failedUserName", "Username maximum 25 characters!");
			return "front-end/register";
		}else {
			User checkUser = userDAO.findByUserName(user.getUserName());
			if (checkUser != null) {
				model.addAttribute("user", user);
				model.addAttribute("failed", "That username is already in use!");
				return "front-end/register";
			}
			
			if(confirmPassword.length() == 0 || password.length() == 0) {
				model.addAttribute("user", user);
				model.addAttribute("failed", "Password is cannot empty!");
				return "front-end/register";
			}else if(!password.equals(confirmPassword)) {
				model.addAttribute("user", user);
				model.addAttribute("failed", "Password Confirmation doesn't match Password");
				return "front-end/register";
			}
			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(password);
			user.setPassWord(encodedPassword);
			user.setEnabled(true);

			model.addAttribute("user", user);
			return "front-end/registerInformation";
		}
	}

	@RequestMapping("/registerInformationUser")
	public String registerInformationUser(@Valid@ModelAttribute("user")User user,
										  BindingResult result,
										  Model model){
		if (result.hasErrors()){
			model.addAttribute("user", user);
			return "front-end/registerInformation";
		}else {
			boolean b1 = userDAO.addNewUser(user);

			Role role = roleDAO.getRoleByName("ROLE_USER");

			User_Role user_Role = new User_Role();
			user_Role.setRole(role);
			user_Role.setUser(user);

			boolean b2 = user_RoleDAO.addNewUserRole(user_Role);

			if(b1&&b2) {
				model.addAttribute("success", "Register successfully!");
				return "front-end/login";
			}else {
				System.out.println("Error when add");
				return "error-page/error-404";
			}
		}
	}
	
	@RequestMapping("/login")
	public String login(@RequestParam(value = "error", required = false)String error,
						Model model) {
		if (error != null) {
			model.addAttribute("failed", "Username or password is incorrect!");
		}

		return "front-end/login";
	}

	@RequestMapping("/logout")
	public String logout(Model model) {
		boolean isLogin = false;
		model.addAttribute("isLogin", isLogin);
		
		return "redirect:/home";
	}
}
