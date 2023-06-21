package bookshop.controller;

import bookshop.dao.*;
import bookshop.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Math.round;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private User_RoleDAO userRoleDAO;
	@Autowired
	private BooksDAO booksDAO;
	@Autowired
	private Books_DetailsDAO books_detailsDAO;
	@Autowired
	private FormatsDAO formatsDAO;
	@Autowired
	private SalesDAO salesDAO;
	@Autowired
	private Books_GenresDAO booksGenresDAO;
	@Autowired
	private AuthorDAO authorDAO;
	@Autowired
	private GenresDAO genresDAO;
	@Autowired
	private OrdersDAO ordersDAO;
	@Autowired
	private Orders_DetailsDAO ordersDetailsDAO;
	@Autowired
	private RoleDAO roleDAO;

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

	public void updateTotalPriceOrder(){
		List<Orders> orders = ordersDAO.loadAllOrder();
		for (Orders o : orders){
			List<Orders_Details> ordersDetails = ordersDetailsDAO.loadOrdersDetailsByOrderId(o.getId());

			float totalPriceOrder = 0;
			for (Orders_Details od : ordersDetails){
				System.out.println(od.getOrders().getEmail());
				float myPrice = od.getTotalPrice();
				totalPriceOrder += myPrice;

				DecimalFormat df = new DecimalFormat("#.##");
				totalPriceOrder = Float.parseFloat(df.format(totalPriceOrder));
			}

			o.setTotalPriceOrder(totalPriceOrder);

			boolean b = ordersDAO.updateOrders(o);

			if (!b){
				System.out.println("ERROR");
			}
		}
	}

	public void updateStatusOrderByStatusBookDetail(){
		List<Orders> ordersList = ordersDAO.loadAllOrder();
		for (Orders o : ordersList){
			if (o.getStatus() == 3){
				List<Orders_Details> ordersDetailsList = ordersDetailsDAO.loadOrdersDetailsByOrderId(o.getId());

				for (Orders_Details od : ordersDetailsList){
					if (od.getBooksDetails().getAvailable()) o.setStatus(0);
				}

				boolean b = ordersDAO.updateOrders(o);

				if (!b){
					System.out.println("ERROR");
				}
			}
		}
	}

	public void updateStatusBookDetail(){
		LocalDate currentDate = LocalDate.now();
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		Date today = null;
		try {
			today = sf.parse(currentDate.getDayOfMonth()+"/"+currentDate.getMonthValue()+"/"+currentDate.getYear());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		List<Books_Details> booksDetailsList = books_detailsDAO.loadAllBookDetail();
		for (Books_Details bd : booksDetailsList){
			int result = bd.getPublication_date().compareTo(today);

			bd.setAvailable(result <= 0 && bd.getQuantityStock() > 0);

			boolean b = books_detailsDAO.updateBookDetail(bd);

			if (!b){
				System.out.println("ERROR");
			}
		}
	}

	@RequestMapping("/welcome")
	public String adminPage(Model model) {
		model.addAttribute("mess", "Welcome to admin page");

		updateSalesStatus();
		updatePriceSale();
		updateStatusBookDetail();
		updateTotalPriceOrder();
		updateStatusOrderByStatusBookDetail();

		// Get the information of admin login
		CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user", user);

		List<Books> books = booksDAO.loadAllBooks();
		for (Books b : books){
			Integer totalStock = 0;

			List<Books_Details> formatsByBookId = books_detailsDAO.getFormatsByBookId(b.getId());
			for (Books_Details bd : formatsByBookId){
				totalStock+=bd.getQuantityStock();
			}

			b.setTotalStock(totalStock);

			boolean b1 = booksDAO.updateBook(b);

			if (!b1){
				System.out.println("ERROR");
			}
		}

		LocalDate localDate = LocalDate.now();
		ZoneId zoneId = ZoneId.systemDefault(); // Use the system's default time zone or specify a desired time zone
		ZonedDateTime zonedDateTime = localDate.atStartOfDay(zoneId);
		Instant instant = zonedDateTime.toInstant();
		Date currentDate = Date.from(instant);

		List<Sales> salesList = salesDAO.loadAllSales();
		for (Sales sales : salesList){
			if (sales.getStart_date().after(currentDate)){
				sales.setStatus(false);
			}else if (sales.getEnd_date().before(currentDate)){
				sales.setStatus(false);
			}else {
				sales.setStatus(true);
			}
		}

		return "back-end/admin";
	}

	@RequestMapping("/loadAllAcc")
	public String loadAllUsers(Model model){
		List<User_Role> userRoles = userRoleDAO.loadAccByRoleId(2L);

		model.addAttribute("listUsers", userRoles);
		model.addAttribute("accountManagementActive", "active");
		model.addAttribute("loadAllAccActive", "active");

		return "back-end/loadAllUsers";
	}

	@RequestMapping("/loadAllAdmin")
	public String loadAllAdmin(Model model){
		// Get the information of all admin
		// ROLE_ADMIN has id = 1 (in database)
		List<User_Role> listAdmin = userRoleDAO.loadAccByRoleId(1L);
		model.addAttribute("listAdmin", listAdmin);

		model.addAttribute("accountManagementActive", "active");
		model.addAttribute("loadAllAdminActive", "active");

		return "back-end/loadAllAdmin";
	}

	@RequestMapping("/detailUser")
	public String detailUser(@RequestParam("id")Long id, Model model){
		User userById = userDAO.getUserById(id);
		model.addAttribute("userById", userById);

		model.addAttribute("accountManagementActive", "active");
		model.addAttribute("loadAllAdminActive", "active");

		return "back-end/detailUser";
	}

	@RequestMapping("/loadAllBooks")
	public String loadAllBooks(Model model){
		List<Books> listBooks = booksDAO.loadAllBooks();
		model.addAttribute("listBooks", listBooks);
		List<Books_Details> booksDetailsList = books_detailsDAO.loadAllBookDetail();

		for (Books b : listBooks){
			int totalSold = 0;
			int totalFormat = 0;

			for (Books_Details bd : booksDetailsList){
				if (bd.getBooks().getId().equals(b.getId())){
					totalSold += bd.getQuantitySold();
					totalFormat++;
				}
			}

			b.setTotalFormat(totalFormat);
			b.setTotalSold(totalSold);
			boolean b1 = booksDAO.updateBook(b);

			if (!b1){
				System.out.println("ERROR");
			}
		}

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllBookActive", "active");

		return "back-end/loadAllBooks";
	}

	@RequestMapping("/detailBook")
	public String detailBooks(@RequestParam("id")Integer id, Model model){
		Books detailsBookById = booksDAO.getDetailsBookById(id);
		model.addAttribute("b", detailsBookById);

		List<Books_Details> listFormats = books_detailsDAO.getFormatsByBookId(id);
		model.addAttribute("listFormats", listFormats);

		List<Books_Genres> listGenres = booksGenresDAO.loadBooksGenresByBookId(id);
		model.addAttribute("listGenres", listGenres);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllBookActive", "active");

		return "back-end/detailBook";
	}

	@RequestMapping("/getDetailFormat")
	public String getDetailFormat(@RequestParam("id")Integer id, Model model){
		Books_Details booksDetailsById = books_detailsDAO.getBooksDetailsById(id);
		model.addAttribute("b", booksDetailsById);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllBookActive", "active");

		return "back-end/detailBookFormat";
	}

	@RequestMapping("/initAddNewBookDetail")
	public String initAddNewBookDetail(@RequestParam("id")Integer id, Model model){
		Books detailsBookById = booksDAO.getDetailsBookById(id);
		model.addAttribute("detailsBookById", detailsBookById);

		Books_Details booksDetails = new Books_Details();
		model.addAttribute("bookDetails", booksDetails);

		List<Formats> formats = formatsDAO.loadALlFormats();
		model.addAttribute("listFormat", formats);

		List<Sales> sales = salesDAO.loadAllSales();
		model.addAttribute("listSales", sales);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllBookActive", "active");

		return "back-end/addNewBookDetail";
	}

	@RequestMapping("/addNewBookDetail")
	public String addNewBookDetail(@Valid@ModelAttribute("bookDetails")Books_Details booksDetails,
								   BindingResult result,
								   @RequestParam("bookId")Integer id,
								   Model model){
		LocalDate currentDate = LocalDate.now();
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		Date today = null;
		try {
			today = sf.parse(currentDate.getDayOfMonth()+"/"+currentDate.getMonthValue()+"/"+currentDate.getYear());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		Books books = booksDAO.getDetailsBookById(id);
		booksDetails.setBooks(books);
		booksDetails.setQuantitySold(0);

		if (result.hasErrors()){
			returnInitAddNewBookDetail(id, booksDetails, model);
			model.addAttribute("failed", "Add new book format is failed!");
			return "back-end/addNewBookDetail";
		}else{
			booksDetails.setAvailable(!booksDetails.getPublication_date().after(today) && booksDetails.getQuantityStock() != 0);
			if (booksDetails.getBooks().getSales() == null){
				booksDetails.setPriceAfterSale((float) 0);
			}else {
				float priceAfterSale = booksDetails.getPrice()*(1-booksDetails.getBooks().getSales().getPercentage()/100);
				booksDetails.setPriceAfterSale(priceAfterSale);
			}

			boolean b = checkIsFormatExist(booksDetails, id);
			if (b){
				model.addAttribute("errorFormat", true);
				returnInitAddNewBookDetail(id, booksDetails, model);
				return "back-end/addNewBookDetail";
			}else{
				boolean b1 = books_detailsDAO.addNewBooksDetails(booksDetails);

				if (b1){
					Books_Details booksDetailsById = books_detailsDAO.getBooksDetailsById(booksDetails.getId());
					model.addAttribute("b", booksDetailsById);
					model.addAttribute("bookManagementActive", "active");
					model.addAttribute("listAllBookActive", "active");
					return "back-end/detailBookFormat";
				}else{
					return "error-page/error-500";
				}
			}
		}
	}

	@RequestMapping("/preUpdateBookFormat")
	public String updateBookFormat(@RequestParam("id")Integer id, Model model){
		Books_Details booksDetailsById = books_detailsDAO.getBooksDetailsById(id);
		model.addAttribute("book", booksDetailsById);

		List<Sales> listSales = salesDAO.loadAllSales();
		model.addAttribute("listSales", listSales);

		List<Formats> listFormats = formatsDAO.loadALlFormats();
		model.addAttribute("listFormats", listFormats);

		model.addAttribute("formatName", booksDetailsById.getFormats().getName());
		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllBookActive", "active");

		return "back-end/updateBookFormat";
	}

	@RequestMapping("/updateBookFormat")
	public String updateBookFormat(@Valid@ModelAttribute("book")Books_Details booksDetails, BindingResult result,
								   @RequestParam("formatName")String formatName,
								   Model model){
		LocalDate currentDate = LocalDate.now();
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		Date today = null;
		try {
			today = sf.parse(currentDate.getDayOfMonth()+"/"+currentDate.getMonthValue()+"/"+currentDate.getYear());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		Integer id = booksDetails.getFormats().getId();
		Formats formatById = formatsDAO.getFormatById(id);
		booksDetails.setFormats(formatById);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllBookActive", "active");

		boolean formatExist = checkIsFormatExist(booksDetails, booksDetails.getFormats().getId());

		if (result.hasErrors()){
			updateBookFormatFailed(booksDetails, model, formatName);
			model.addAttribute("failed", "Updated is failed!");

			return "back-end/updateBookFormat";
		}else if (booksDetails.getFormats().getName().equalsIgnoreCase(formatName)){
			int compareTo = booksDetails.getPublication_date().compareTo(today);

			booksDetails.setAvailable(compareTo <= 0 && booksDetails.getQuantityStock() > 0);

			boolean b = books_detailsDAO.updateBookDetail(booksDetails);

			if (b){
				returnDetailBookFormat(booksDetails, model);
				model.addAttribute("success", "Update is successfully!");

				return "back-end/detailBookFormat";
			}else {
				updateBookFormatFailed(booksDetails, model, formatName);
				model.addAttribute("failed", "Updated is failed!");

				return "back-end/updateBookFormat";
			}
		}else {
			if (formatExist){
				updateBookFormatFailed(booksDetails, model, formatName);

				model.addAttribute("failed", "Updated is failed!");
				model.addAttribute("errorFormat", true);

				return "back-end/updateBookFormat";
			}else {
				if (booksDetails.getPublication_date().after(today) && booksDetails.getQuantityStock() > 0){
					booksDetails.setAvailable(true);
				}

				boolean b = books_detailsDAO.updateBookDetail(booksDetails);

				if (b){
					returnDetailBookFormat(booksDetails, model);
					model.addAttribute("success", "Update is successfully!");

					return "back-end/detailBookFormat";
				}else {
					updateBookFormatFailed(booksDetails, model, formatName);
					model.addAttribute("failed", "Updated is failed!");

					return "back-end/updateBookFormat";
				}
			}
		}
	}

	@RequestMapping("/deleteBookFormat")
	public String deleteBookFormat(@RequestParam("id")Integer id, Model model){
		Books_Details booksDetailsById = books_detailsDAO.getBooksDetailsById(id);
		boolean b = books_detailsDAO.deleteBookDetail(id);

		returnBookDetail(model, booksDetailsById);

		if (b){
			model.addAttribute("success", "Delete is successfully!");
		}else {
			model.addAttribute("failed", "Delete is failed!");
		}

		return "back-end/detailBook";
	}

	@RequestMapping("/initAddNewFormat")
	public String initAddNewFormat(Model model){
		Formats formats = new Formats();
		model.addAttribute("format", formats);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllFormatActive", "active");

		List<Formats> listFormats = formatsDAO.loadALlFormats();
		model.addAttribute("listFormats", listFormats);

		List<Books_Details> booksDetailsList = books_detailsDAO.loadAllBookDetail();

		for (Formats f : listFormats){
			int totalBook = 0;

			for (Books_Details bd : booksDetailsList){
				if (bd.getFormats().getId().equals(f.getId())){
					totalBook++;
				}
			}

			f.setTotalBook(totalBook);
			boolean b = formatsDAO.updateFormat(f);

			if (!b){
				System.out.println("ERROR");
			}
		}

		return "back-end/addAndListFormat";
	}

	@RequestMapping("/addNewFormat")
	public String addNewFormat(@Valid@ModelAttribute("format")Formats formats, BindingResult result, Model model){
		if (result.hasErrors()){
			addNewFormatFailed(formats, model);
			model.addAttribute("failed", "Add new format is failed!");

		}else{
			formats.setTotalBook(0);
			boolean b = formatsDAO.addNewFormat(formats);

			if (b){
				returnAddAndListFormat(model);
				model.addAttribute("success", "Add new format successfully!");
			}else{
				addNewFormatFailed(formats, model);
				model.addAttribute("failed", "Add new format is failed!");
			}
		}
		return "back-end/addAndListFormat";
	}

	@RequestMapping("/deleteFormat")
	public String deleteFormat(@RequestParam("id")Integer id, Model model){
		boolean b = formatsDAO.deleteFormatById(id);

		returnAddAndListFormat(model);
		if (b){
			model.addAttribute("success", "Delete successfully!");
		}else{
			model.addAttribute("failed", "Delete failed!");
		}
		return "back-end/addAndListFormat";
	}

	@RequestMapping("/preUpdateFormat")
	public String preUpdateFormat(@RequestParam("id")Integer id, Model model){
		Formats formatById = formatsDAO.getFormatById(id);
		model.addAttribute("formatById", formatById);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllFormatActive", "active");

		return "back-end/updateFormat";
	}

	@RequestMapping("/detailFormat")
	public String detailFormat(@RequestParam("id")Integer id, Model model){
		Formats formatById = formatsDAO.getFormatById(id);
		model.addAttribute("formatById", formatById);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllFormatActive", "active");

		List<Books_Details> booksDetailsList = books_detailsDAO.loadAllBookByFormatId(id);
		model.addAttribute("bookList", booksDetailsList);

		return "back-end/detailFormat";
	}

	@RequestMapping("/updateFormat")
	public String updateFormat(@Valid@ModelAttribute("formatById")Formats formats, BindingResult result, Model model){
		if (result.hasErrors()){
			updateFormatFailed(formats, model);
			model.addAttribute("failed", "Updated format is failed!");
			return "back-end/updateFormat";
		}else{
			boolean b = formatsDAO.updateFormat(formats);

			if (b){
				updateFormatSuccess(model);
				model.addAttribute("success", "Updated successfully!");
				return "back-end/addAndListFormat";
			}else{
				updateFormatFailed(formats, model);
				model.addAttribute("failed", "Updated format is failed!");
				return "back-end/updateFormat";
			}
		}
	}

	@RequestMapping("/initAddNewBook")
	public String initAddNewBook(Model model){
		Books books = new Books();
		model.addAttribute("books", books);

		List<Authors> listAuthor = authorDAO.loadAllAuthors();
		model.addAttribute("listAuthor", listAuthor);

		List<Genres> listGenres = genresDAO.loadAllGenres();
		model.addAttribute("listGenres", listGenres);

		List<Sales> salesList = salesDAO.loadAllSales();
		List<Sales> sales = new ArrayList<>();
		for (Sales s : salesList){
			if (s.getStatus()){
				sales.add(s);
			}
		}
		model.addAttribute("saleList", sales);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllBookActive", "active");

		return "back-end/addNewBook";
	}

	@RequestMapping("/addNewBook")
	public String addNewBook(@Valid@ModelAttribute("books")Books books,
							 BindingResult result,
							 @RequestParam(value = "selectGenres", required = false) Integer[] selectGenres,
							 @RequestParam("bookImg") MultipartFile fileImg,
							 Model model,
							 HttpServletRequest request){
		if (result.hasErrors()){
			addNewBookFailed(books, model);
			model.addAttribute("failed", "Add new book is failed!");
			return "back-end/addNewBook";
		}else if (selectGenres == null){
			addNewBookFailed(books, model);
			model.addAttribute("errorGenre", true);
			model.addAttribute("failed", "Add this book is failed!");
			return "back-end/addNewBook";
		}else{
			books.setTotalStock(0);

			Date currentDate = new Date();
			// Format the date as yyyy-MM-dd
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = formatter.format(currentDate);

			// Set the formatted date to start_date
			try {
				books.setCreate_date(formatter.parse(formattedDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			books.setTotalFormat(0);
			books.setTotalSold(0);

			if (books.getSales().getId() == 0){
				books.setSales(null);
			}

			uploadSetImage(request, fileImg, books);

			boolean b = booksDAO.addNewBook(books);

			if (b){
				for(Integer id : selectGenres){
					Books_Genres booksGenres = new Books_Genres();
					Genres genreById = genresDAO.getGenreById(id);
					booksGenres.setBooks(books);
					booksGenres.setGenres(genreById);
					boolean b1 = booksGenresDAO.addNewBookGenres(booksGenres);

					if (!b1){
						addNewBookFailed(books, model);
						model.addAttribute("failed", "Add new book is failed!");
						return "back-end/addNewBook";
					}
				}

				addNewBookSuccess(model);
				model.addAttribute("success", "Add new book successfully!");

				return "back-end/loadAllBooks";
			}else {
				addNewBookFailed(books, model);
				model.addAttribute("failed", "Add new book is failed!");
				return "back-end/addNewBook";
			}
		}
	}

	public void uploadSetImage(HttpServletRequest request, MultipartFile fileImg, Books books){
		String path = request.getServletContext().getRealPath("resources/images");
		File f = new File(path);

		File destination = new File(f.getAbsolutePath()+"/"+fileImg.getOriginalFilename());

		if (!destination.exists()){
			try {
				Files.write(destination.toPath(), fileImg.getBytes(), StandardOpenOption.CREATE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		books.setImage(fileImg.getOriginalFilename());
	}

	@RequestMapping("/preUpdateBook")
	public String preUpdateBook(@RequestParam("id")Integer id,Model model){
		Books detailsBookById = booksDAO.getDetailsBookById(id);
		model.addAttribute("book", detailsBookById);

		if (detailsBookById.getSales() == null){
			model.addAttribute("saleNull", true);
		}else {
			model.addAttribute("saleName", detailsBookById.getSales().getName());
		}

		List<Authors> listAuthor = authorDAO.loadAllAuthors();
		model.addAttribute("listAuthor", listAuthor);
		model.addAttribute("authorId", detailsBookById.getAuthors().getId());

		List<Books_Genres> booksGenresList = booksGenresDAO.loadBooksGenresByBookId(id);
		model.addAttribute("booksGenresList", booksGenresList);

		List<Genres> listGenres = genresDAO.loadAllGenres();
		model.addAttribute("listGenres", listGenres);

		List<Sales> salesList = salesDAO.loadAllSales();
		List<Sales> sales = new ArrayList<>();
		for (Sales s : salesList){
			if (s.getStatus()){
				sales.add(s);
			}
		}
		model.addAttribute("listSales", sales);

		return "back-end/updateBook";
	}

	@RequestMapping("/updateBook")
	public String updateBook(@Valid@ModelAttribute("book")Books books,
							 BindingResult result,
							 @RequestParam(value = "selectGenres", required = false) Integer[] selectGenres,
							 @RequestParam("bookImg") MultipartFile fileImg,
							 Model model,
							 HttpServletRequest request){
		if (books.getSales().getId() == 0){
			books.setSales(null);
		}else {
			Sales salesById = salesDAO.getSalesById(books.getSales().getId());
			books.setSales(salesById);
		}

		if (result.hasErrors()){
			returnPreUpdateBook(books, model);
			model.addAttribute("failed", "Update this book is failed!");
			return "back-end/updateBook";
		}else if (selectGenres == null){
			returnPreUpdateBook(books, model);
			model.addAttribute("errorGenre", true);
			model.addAttribute("failed", "Update this book is failed!");
			return "back-end/updateBook";
		}else{
			uploadSetImage(request, fileImg, books);
			boolean b = booksDAO.updateBook(books);

			if (b){
				List<Books_Genres> listBookGenres = booksGenresDAO.getBookGenresByBookId(books.getId());
				for(Books_Genres bg : listBookGenres){
					boolean b1 = booksGenresDAO.deleteBookGenresByBookId(bg);

					if (!b1){
						System.out.println("ERROR");
					}
				}

				for(Integer id : selectGenres){
					Books_Genres booksGenres = new Books_Genres();
					Genres genreById = genresDAO.getGenreById(id);
					booksGenres.setBooks(books);
					booksGenres.setGenres(genreById);
					boolean b1 = booksGenresDAO.addNewBookGenres(booksGenres);

					if (!b1){
						System.out.println("ERROR");
					}
				}

				addNewBookSuccess(model);
				model.addAttribute("success", "Update is successfully!");
				return "back-end/loadAllBooks";
			}else {
				returnInitAddNewBook(books, model);
				model.addAttribute("failed", "Update this book is failed! 789");
				return "back-end/updateBook";
			}
		}
	}

	@RequestMapping("/deleteBook")
	public String deleteBook(@RequestParam("id")Integer id, Model model){
		List<Books_Genres> booksGenres = booksGenresDAO.loadBooksGenresByBookId(id);
		for (Books_Genres bg : booksGenres){
			boolean b = booksGenresDAO.deleteBookGenresByBookId(bg);

			if (!b){
				System.out.println("ERROR");
			}
		}

		List<Books_Details> formatsByBookId = books_detailsDAO.getFormatsByBookId(id);
		for (Books_Details bd : formatsByBookId){
			boolean b = books_detailsDAO.deleteBookDetail(bd.getId());

			if (!b){
				System.out.println("ERROR");
			}
		}

		boolean b = booksDAO.deleteBook(id);

		List<Books> listBooks = booksDAO.loadAllBooks();

		model.addAttribute("listBooks", listBooks);
		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllBookActive", "active");

		if (b){
			model.addAttribute("success", "Delete successfully!");
		}else {
			model.addAttribute("failed", "Delete failed!");
		}

		return "back-end/loadAllBooks";
	}

	@RequestMapping("/initAddNewGenre")
	public String initAddNewGenre(Model model){
		Genres genres = new Genres();
		model.addAttribute("genre", genres);

		List<Genres> genresList = genresDAO.loadAllGenres();
		model.addAttribute("genresList", genresList);

		List<Books_Genres> booksGenresList = booksGenresDAO.loadAllBookGenre();

		for (Genres g : genresList){
			int totalBook = 0;

			for (Books_Genres bg : booksGenresList){
				if (bg.getGenres().getId().equals(g.getId())){
					totalBook++;
				}
			}

			g.setTotalBook(totalBook);
			boolean b = genresDAO.updateGenre(g);

			if (!b){
				System.out.println("ERROR");
			}
		}

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllGenreActive", "active");

		return "back-end/addAndListGenre";
	}

	@RequestMapping("/addNewGenre")
	public String addNewGenre(@Valid@ModelAttribute("genre")Genres genres, BindingResult result, Model model){
		if (result.hasErrors()){
			addNewGenreFailed(genres, model);
			model.addAttribute("failed", "Add new genre failed!");

		}else{
			genres.setTotalBook(0);
			boolean b = genresDAO.addNewGenre(genres);

			if (b){
				addNewGenreSuccess(model);
				model.addAttribute("success", "Add new genre successfully!");
			}else{
				addNewGenreFailed(genres, model);
				model.addAttribute("failed", "Add new genre failed!");
			}
		}
		return "back-end/addAndListGenre";
	}

	@RequestMapping("/deleteGenre")
	public String deleteGenre(@RequestParam("id")Integer id, Model model){
		Genres genreById = genresDAO.getGenreById(id);
		if (genreById == null){
			return "redirect:/admin/initAddNewGenre";
		}

		boolean b = genresDAO.deleteGenre(id);

		Genres genres = new Genres();
		model.addAttribute("genre", genres);
		List<Genres> genresList = genresDAO.loadAllGenres();
		model.addAttribute("genresList", genresList);

		if (b){
			model.addAttribute("success", "Delete successfully!");
		}else {
			model.addAttribute("failed", "This genre cannot be delete because there is more than 1 book in this genre");
		}

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllGenreActive", "active");
		return "back-end/addAndListGenre";
	}

	@RequestMapping("/preUpdateGenre")
	public String preUpdateGenre(@RequestParam("id")Integer id ,Model model){
		Genres genreById = genresDAO.getGenreById(id);
		model.addAttribute("genre", genreById);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllGenreActive", "active");

		return "back-end/updateGenre";
	}

	@RequestMapping("/detailGenre")
	public String detailGenre(@RequestParam("id")Integer id, Model model){
		Genres genreById = genresDAO.getGenreById(id);
		model.addAttribute("genre", genreById);

		List<Books_Genres> booksGenres = booksGenresDAO.loadAllBookByGenreId(id);
		model.addAttribute("listBook", booksGenres);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllGenreActive", "active");

		return "back-end/detailGenre";
	}

	@RequestMapping("/updateGenre")
	public String updateGenre(@Valid@ModelAttribute("genre")Genres genres, BindingResult result, Model model){
		if (result.hasErrors()){
			updateGenreFailed(genres, model);
			model.addAttribute("failed", "Updated is failed!");

			return "back-end/updateGenre";
		}else {
			boolean b = genresDAO.updateGenre(genres);

			if (b){
				updateGenreSuccess(model);
				model.addAttribute("success", "Update successfully!");

				return "back-end/addAndListGenre";
			}else {
				updateGenreFailed(genres, model);
				model.addAttribute("failed", "Update is failed!");

				return "back-end/updateGenre";
			}
		}
	}

	@RequestMapping("/loadAllAuthor")
	public String loadAllAuthor(Model model){
		List<Authors> authorsList = authorDAO.loadAllAuthors();
		model.addAttribute("authorList", authorsList);


		List<Books> booksList = booksDAO.loadAllBooks();

		for (Authors a : authorsList){
			int totalBook = 0;

			for (Books b : booksList){
				if (b.getAuthors().getId().equals(a.getId())){
					totalBook++;
				}
			}

			a.setTotalBook(totalBook);
			boolean b = authorDAO.updateAuthor(a);

			if (!b){
				System.out.println("ERROR");
			}
		}

		Authors authors = new Authors();

		model.addAttribute("author", authors);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllAuthorActive", "active");
		model.addAttribute("listAuthorActive", "active");

		return "back-end/loadAllAuthor";
	}

	@RequestMapping("/addNewAuthor")
	public String addNewAuthor(@Valid@ModelAttribute("author")Authors authors, BindingResult result, Model model){
		if (result.hasErrors()){
			returnAddNewAuthor(authors, model);
			model.addAttribute("failed", "Add new author failed!");
		}else {
			authors.setTotalBook(0);
			boolean b = authorDAO.addNewAuthor(authors);

			if (b){
				Authors author = new Authors();
				returnLoadAllAuthor(model, author);

				model.addAttribute("success", "Add new author successfully!");

			}else {
				returnAddNewAuthor(authors, model);
				model.addAttribute("failed", "Add new author failed!");

				return "back-end/loadAllAuthor";
			}
		}
		return "back-end/loadAllAuthor";
	}

	@RequestMapping("/deleteAuthor")
	public String deleteAuthor(@RequestParam("id")Integer id, Model model){
		boolean b = authorDAO.deleteAuthor(id);
		Authors authors = new Authors();
		returnLoadAllAuthor(model, authors);

		if (b){
			model.addAttribute("success", "Delete author successfully!");
		}else {
			model.addAttribute("failed", "This author cannot be deleted because there are more than 1 books by this author!");
		}
		return "back-end/loadAllAuthor";
	}

	@RequestMapping("/preUpdateAuthor")
	public String preUpdateAuthor(@RequestParam("id")Integer id, Model model){
		Authors authorById = authorDAO.getAuthorById(id);
		model.addAttribute("author", authorById);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllAuthorActive", "active");

		return "back-end/updateAuthor";
	}

	@RequestMapping("/detailAuthor")
	public String detailAuthor(@RequestParam("id")Integer id, Model model){
		Authors authorById = authorDAO.getAuthorById(id);
		model.addAttribute("author", authorById);

		List<Books> booksList = booksDAO.loadAllBookByAuthorId(id);
		model.addAttribute("bookList", booksList);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllAuthorActive", "active");

		return "back-end/detailAuthor";
	}

	@RequestMapping("/updateAuthor")
	public String updateAuthor(@Valid@ModelAttribute("author")Authors authors, BindingResult result, Model model){
		if (result.hasErrors()){
			updateAuthorFailed(authors, model);
			model.addAttribute("failed", "Update failed!");

			return "back-end/updateAuthor";
		}else {
			boolean b = authorDAO.updateAuthor(authors);

			if (b){
				updateAuthorSuccess(model);
				model.addAttribute("success", "Update author successfully!");

				return "back-end/loadAllAuthor";
			}else {
				updateAuthorFailed(authors, model);
				model.addAttribute("failed", "Update failed!");

				return "back-end/updateAuthor";
			}
		}
	}

	@RequestMapping("/initAddNewSales")
	public String initAddNewSales(Model model){
		Sales sales = new Sales();
		model.addAttribute("sale", sales);

		List<Sales> salesList = salesDAO.loadAllSales();
		for (Sales s : salesList){
			List<Books> bookBySaleId = booksDAO.getBookBySaleId(s.getId());
			s.setTotalBook(bookBySaleId.size());
			boolean b = salesDAO.updateSales(s);

			if (!b){
				System.out.println("ERROR");
			}
		}

		model.addAttribute("salesList", salesList);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllSalesActive", "active");

		return "back-end/addAndListSales";
	}

	public void uploadFileSaleImg(HttpServletRequest request, MultipartFile fileImg, Sales sales){
		String path = request.getServletContext().getRealPath("resources/images");
		File f = new File(path);

		File destination = new File(f.getAbsolutePath()+"/"+fileImg.getOriginalFilename());

		if (!destination.exists()){
			try {
				Files.write(destination.toPath(), fileImg.getBytes(), StandardOpenOption.CREATE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		sales.setSaleImg(fileImg.getOriginalFilename());
	}

	@RequestMapping("/addNewSale")
	public String addNewSale(@Valid@ModelAttribute("sale")Sales sales,
							 BindingResult result,
							 @RequestParam("saleImage")MultipartFile fileImg,
							 Model model,
							 HttpServletRequest request){
		if (result.hasErrors()){
			for (FieldError error : result.getFieldErrors()) {
				System.out.println("Error in field: " + error.getField());
				System.out.println("Error message: " + error.getDefaultMessage());
			}

			changeSalesFailed(model, sales);
			model.addAttribute("failed", "Add new sales is failed!");
		}else if (sales.getEnd_date().before(sales.getStart_date())){
			changeSalesFailed(model, sales);
			model.addAttribute("dateError", true);
		}else {
			uploadFileSaleImg(request, fileImg, sales);
			LocalDate localDate = LocalDate.now();
			ZoneId zoneId = ZoneId.systemDefault(); // Use the system's default time zone or specify a desired time zone
			ZonedDateTime zonedDateTime = localDate.atStartOfDay(zoneId);
			Instant instant = zonedDateTime.toInstant();
			Date currentDate = Date.from(instant);

			if (sales.getStart_date().after(currentDate)){
				sales.setStatus(false);
			}else if (sales.getEnd_date().before(currentDate)){
				sales.setStatus(false);
			}else {
				sales.setStatus(true);
			}

			sales.setTotalBook(0);

			boolean b = salesDAO.addNewSale(sales);

			if (b){
				changeSalesSuccess(model);
				model.addAttribute("success", "Add new sales is successfully!");

			}else {
				changeSalesFailed(model, sales);
				model.addAttribute("failed", "Add new sales is failed!");

			}
		}
		return "back-end/addAndListSales";
	}

	@RequestMapping("/deleteSales")
	public String deleteSales(@RequestParam("id")Integer id, Model model){
		List<Books> bookBySaleId = booksDAO.getBookBySaleId(id);
		for (Books b : bookBySaleId){
			b.setSales(null);
			boolean b1 = booksDAO.updateBook(b);

			if (!b1){
				System.out.println("ERROR");
			}
		}

		boolean b = salesDAO.deleteSale(id);

		changeSalesSuccess(model);
		if (b){
			model.addAttribute("success", "Delete sale is successfully!");
		}else {
			model.addAttribute("failed", "Delete sale is failed!");
		}
		return "back-end/addAndListSales";
	}

	@RequestMapping("/preUpdateSale")
	public String preUpdateSale(@RequestParam("id")Integer id, Model model){
		Sales salesById = salesDAO.getSalesById(id);
		model.addAttribute("sale", salesById);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllSalesActive", "active");

		return "back-end/updateSale";
	}

	@RequestMapping("/updateSale")
	public String updateSale(@Valid@ModelAttribute("sale")Sales sales,
							 BindingResult result,
							 @RequestParam("saleImage") MultipartFile fileImg,
							 Model model,
							 HttpServletRequest request){
		uploadFileSaleImg(request, fileImg, sales);

		if (result.hasErrors()){
			changeSalesFailed(model, sales);
			model.addAttribute("failed", "Update sale is failed! 123");

			return "back-end/updateSale";
		}else if (sales.getStart_date().after(sales.getEnd_date())){
			changeSalesFailed(model, sales);
			model.addAttribute("dateError", true);

			return "back-end/updateSale";
		}else {
			LocalDate localDate = LocalDate.now();
			ZoneId zoneId = ZoneId.systemDefault(); // Use the system's default time zone or specify a desired time zone
			ZonedDateTime zonedDateTime = localDate.atStartOfDay(zoneId);
			Instant instant = zonedDateTime.toInstant();
			Date currentDate = Date.from(instant);

			if (sales.getStart_date().after(currentDate)){
				sales.setStatus(false);
			}else if (sales.getEnd_date().before(currentDate)){
				sales.setStatus(false);
			}else {
				sales.setStatus(true);
			}

			boolean b = salesDAO.updateSales(sales);

			if (b){
				changeSalesSuccess(model);
				model.addAttribute("success", "Update sale is successfully!");

				return "back-end/addAndListSales";
			}else {
				changeSalesFailed(model, sales);
				model.addAttribute("failed", "Update sale is failed!");

				return "back-end/updateSale";
			}
		}
	}

	@RequestMapping("/detailSale")
	public String detailSale(@RequestParam("id")Integer id, Model model){
		Sales salesById = salesDAO.getSalesById(id);
		salesById.setTotalBook(Math.toIntExact(booksDAO.getTotalBookBySaleId(id)));
		model.addAttribute("sale", salesById);

		List<Books> bookBySaleId = booksDAO.getBookBySaleId(id);
		model.addAttribute("bookBySaleId", bookBySaleId);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllSalesActive", "active");

		return "back-end/detailSale";
	}

	@RequestMapping("/removeBookSale")
	public String removeBookSale(@RequestParam("id")Integer id, Model model){
		Books detailsBookById = booksDAO.getDetailsBookById(id);
		int saleId = detailsBookById.getSales().getId();
		detailsBookById.setSales(null);
		boolean b = booksDAO.updateBook(detailsBookById);

		if (!b){
			System.out.println("ERROR");
		}

		model.addAttribute("success", "Remove is successfully!");

		Sales salesById = salesDAO.getSalesById(saleId);
		salesById.setTotalBook(Math.toIntExact(booksDAO.getTotalBookBySaleId(saleId)));
		model.addAttribute("sale", salesById);

		List<Books> bookBySaleId = booksDAO.getBookBySaleId(saleId);
		model.addAttribute("bookBySaleId", bookBySaleId);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllSalesActive", "active");

		return "back-end/detailSale";
	}

	@RequestMapping("/initAddNewBookSale")
	public String initAddNewBookSale(@RequestParam("saleId")Integer saleId, Model model){
		Sales salesById = salesDAO.getSalesById(saleId);
		model.addAttribute("sale", salesById);

		List<Books> booksList = booksDAO.loadBookNullSale();
		model.addAttribute("booksSaleNull", booksList);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllSalesActive", "active");

		return "back-end/addNewBookSale";
	}

	@RequestMapping("/addNewBookSale")
	public String addNewBookSale(@RequestParam("saleId")Integer saleId,
								 @RequestParam("bookId")Integer bookId,
								 Model model){
		Sales salesById = salesDAO.getSalesById(saleId);
		Books detailsBookById = booksDAO.getDetailsBookById(bookId);
		detailsBookById.setSales(salesById);

		boolean b1 = booksDAO.updateBook(detailsBookById);

		salesById.setTotalBook(Math.toIntExact(booksDAO.getTotalBookBySaleId(salesById.getId())));
		model.addAttribute("sale", salesById);

		List<Books> booksList = booksDAO.loadBookNullSale();
		model.addAttribute("booksSaleNull", booksList);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllSalesActive", "active");

		if (b1){
			model.addAttribute("success", "Add new book to sale is successfully!");
		}else {
			model.addAttribute("failed", "Add new book to sale is failed!");
		}
		return "back-end/addNewBookSale";
	}

	@RequestMapping("/loadAllOrders")
	public String loadAllOrders(Model model){
		List<Orders> ordersList = ordersDAO.loadAllOrder();

		model.addAttribute("ordersActive", "active");
		model.addAttribute("listOrdersActive", "active");

		model.addAttribute("ordersList", ordersList);

		return "back-end/loadAllOrders";
	}

	@RequestMapping("/loadOrdersWithFilter")
	public String loadOrdersWithFilter(@RequestParam(value = "startDate", required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
									   @RequestParam(value = "endDate", required = false)@DateTimeFormat(pattern = "yyyy-MM-dd")Date endDate,
									   @RequestParam(value = "status", required = false)Integer status,
									   Model model){
//		System.out.println(startDate + " " + endDate + " " + status);
		model.addAttribute("ordersActive", "active");
		model.addAttribute("listOrdersActive", "active");

		if (startDate == null && endDate == null && status == -3){
			List<Orders> ordersList = ordersDAO.loadAllOrder();

			model.addAttribute("ordersList", ordersList);
		}

		if (startDate != null && endDate == null && status == -3){
			List<Orders> ordersList = ordersDAO.loadOrdersByDate(startDate);

			model.addAttribute("ordersList", ordersList);
		}

		if (startDate == null && endDate != null && status == -3){
			List<Orders> ordersList = ordersDAO.loadOrdersByDate(endDate);

			model.addAttribute("ordersList", ordersList);
		}

		if (startDate == null && endDate == null && status != -3){
			List<Orders> ordersList = ordersDAO.loadAllOrderByStatus(status);
			System.out.println("YES");
			model.addAttribute("ordersList", ordersList);
		}

		if (startDate != null && endDate == null && status != -3){
			List<Orders> ordersList = ordersDAO.loadOrdersByDateAndStatus(startDate, status);

			model.addAttribute("ordersList", ordersList);
		}

		if (startDate == null && endDate != null && status != -3){
			List<Orders> ordersList = ordersDAO.loadOrdersByDateAndStatus(endDate, status);

			model.addAttribute("ordersList", ordersList);
		}

		if (startDate != null && endDate != null && status == -3){
			List<Orders> ordersList = ordersDAO.loadOrderByStartDateAndEndDate(startDate, endDate);

			model.addAttribute("ordersList", ordersList);
		}

		if (startDate != null && endDate != null && status != -3){
			List<Orders> ordersList = ordersDAO.loadOrderByStartDateEndDateStatus(startDate, endDate, status);

			model.addAttribute("ordersList", ordersList);
		}

		return "back-end/loadAllOrders";
	}

	@RequestMapping("/loadOrdersCurrentDate")
	public String loadOrdersCurrentDate(Model model){
		model.addAttribute("ordersActive", "active");
		model.addAttribute("listOrdersActive", "active");

		List<Orders> ordersList = ordersDAO.loadAllOrderCurrentDate();

		model.addAttribute("ordersList", ordersList);
		return "back-end/loadAllOrders";
	}

	@RequestMapping("detailOder")
	public String detailOder(@RequestParam("id")Integer id, Model model){
		Orders orders = ordersDAO.loadOrderById(id);
		model.addAttribute("orders", orders);

		List<Orders_Details> ordersDetailsList = ordersDetailsDAO.loadOrdersDetailsByOrderId(id);
		model.addAttribute("ordersDetail", ordersDetailsList);

		float totalPrice = (float) 0;
		for (Orders_Details od : ordersDetailsList){
			totalPrice += od.getTotalPrice();
		}
		model.addAttribute("totalPrice", totalPrice);

		List<Orders> ordersList = ordersDAO.loadAllOrderCurrentDate();
		model.addAttribute("ordersList", ordersList);

		model.addAttribute("ordersActive", "active");
		model.addAttribute("listOrdersActive", "active");

		return "back-end/detailsOrders";
	}

	public void loadDetailOrder(Model model, Integer orderId){
		Orders orders = ordersDAO.loadOrderById(orderId);
		model.addAttribute("orders", orders);

		List<Orders_Details> ordersDetailsList = ordersDetailsDAO.loadOrdersDetailsByOrderId(orderId);
		model.addAttribute("ordersDetail", ordersDetailsList);

		float totalPrice = (float) 0;
		for (Orders_Details od : ordersDetailsList){
			totalPrice += od.getTotalPrice()*od.getQuantity();
		}
		model.addAttribute("totalPrice", totalPrice);

		List<Orders> ordersList = ordersDAO.loadAllOrderCurrentDate();
		model.addAttribute("ordersList", ordersList);

		model.addAttribute("ordersActive", "active");
		model.addAttribute("listOrdersActive", "active");
	}

	@RequestMapping("/updateStatusOrders")
	public String updateStatusOrders(@RequestParam("orderId")Integer orderId,
									 @RequestParam("statusId")Integer statusId,
									 Model model){
		//   0 chua xu ly
		//   -1 k xu ly
		//   2 dang rao hang
		//   1 rao hang thanh cong
		//  -2 rao hang k thanh cong
		Orders orders = ordersDAO.loadOrderById(orderId);

		if ((orders.getStatus() == -1 || orders.getStatus() == -2 || orders.getStatus() == 0) && statusId == 1){
			loadDetailOrder(model, orderId);
			model.addAttribute("failed", "This order is already unprocessed or not completed delivery!");

			return "back-end/detailsOrders";
		}

		if (statusId == 1){
			List<Orders_Details> ordersDetails = ordersDetailsDAO.loadOrdersDetailsByOrderId(orderId);
			for (Orders_Details od : ordersDetails){
				Books_Details booksDetailsById = books_detailsDAO.getBooksDetailsById(od.getBooksDetails().getId());

				Integer quantityStock = booksDetailsById.getQuantityStock();
				Integer quantitySold = booksDetailsById.getQuantitySold();

				booksDetailsById.setQuantityStock(quantityStock-od.getQuantity());
				booksDetailsById.setQuantitySold(quantitySold+od.getQuantity());

				boolean b = books_detailsDAO.updateBookDetail(booksDetailsById);

				if (!b){
					System.out.println("ERROR");
				}
			}
		}

		orders.setStatus(statusId);

		boolean b = ordersDAO.updateOrders(orders);

		loadDetailOrder(model, orderId);

		if (b){
			model.addAttribute("success", "You apply that order successfully!");
		}else {
			model.addAttribute("failed", "You apply that order failed!");
		}
		return "back-end/detailsOrders";
	}

	@RequestMapping("/loadOrdersChart")
	public String loadOrdersChart(@RequestParam(value = "numberOfDate", required = false)Integer numberOfDate,
								  Model model) {
		if (numberOfDate == null){
			numberOfDate = 5;
		}

		List<Date> listDate = new ArrayList<>();
		LocalDate currentDate = LocalDate.now();
		for (int i = 0; i <= numberOfDate-1; i++) {
			LocalDate date = currentDate.minusDays(i);

			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

			Date date1;

			try {
				date1 = sf.parse(date.getDayOfMonth()+"/"+date.getMonthValue()+"/"+date.getYear());
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}

			listDate.add(date1);
		}

		model.addAttribute("date", listDate);

		List<Integer> unProcessedList = new ArrayList<>();
		List<Integer> deliveryList = new ArrayList<>();
		List<Integer> deliverySuccessList = new ArrayList<>();
		List<Integer> noProcessingList = new ArrayList<>();
		List<Integer> deliveryFailedList = new ArrayList<>();
		List<Integer> preOrdersList = new ArrayList<>();

		for (Date d : listDate){
			List<Orders> ordersList = ordersDAO.loadOrdersByDate(d);

			int totalUnProcessed = 0, totalDelivery = 0, totalDeliverySuccess = 0,
					totalNoProcessing = 0, totalDeliveryFailed = 0, totalPreOrders = 0;

			for (Orders o : ordersList){
				if (o.getStatus() == 0){
					totalUnProcessed++;
				}
				if (o.getStatus() == -1){
					totalNoProcessing++;
				}
				if (o.getStatus() == 2){
					totalDelivery++;
				}
				if (o.getStatus() == 1){
					totalDeliverySuccess++;
				}
				if (o.getStatus() == -2){
					totalDeliveryFailed++;
				}
				if (o.getStatus() == 3){
					totalPreOrders++;
				}
			}

			unProcessedList.add(totalUnProcessed);
			deliveryList.add(totalDelivery);
			deliverySuccessList.add(totalDeliverySuccess);
			noProcessingList.add(totalNoProcessing);
			deliveryFailedList.add(totalDeliveryFailed);
			preOrdersList.add(totalPreOrders);
		}

		model.addAttribute("unProcessedList", unProcessedList);
		model.addAttribute("deliveryList", deliveryList);
		model.addAttribute("deliverySuccessList", deliverySuccessList);
		model.addAttribute("noProcessingList", noProcessingList);
		model.addAttribute("deliveryFailedList", deliveryFailedList);
		model.addAttribute("preOrdersList", preOrdersList);

		model.addAttribute("ordersActive", "active");
		model.addAttribute("ordersChartActive", "active");

		return "back-end/loadOrdersChart";
	}


	@RequestMapping("/loadOrdersChartWithFilter")
	public String loadOrdersChartWithFilter(@RequestParam("numberOfDate")Integer numberOfDate){
		if (numberOfDate == 5){
			return "redirect:/admin/loadOrdersChart?numberOfDate=5";
		} else if (numberOfDate == 10) {
			return "redirect:/admin/loadOrdersChart?numberOfDate=10";
		} else if (numberOfDate == 15) {
			return "redirect:/admin/loadOrdersChart?numberOfDate=15";
		} else if (numberOfDate == 20) {
			return "redirect:/admin/loadOrdersChart?numberOfDate=20";
		} else if (numberOfDate == 25) {
			return "redirect:/admin/loadOrdersChart?numberOfDate=25";
		} else if (numberOfDate == 30) {
			return "redirect:/admin/loadOrdersChart?numberOfDate=30";
		}else {
			return "redirect:/admin/loadOrdersChart";
		}
	}

	@RequestMapping("/preUpdateOrders")
	public String preUpdateOrders(@RequestParam("id")Integer orderId,Model model){
		Orders orders = ordersDAO.loadOrderById(orderId);
		model.addAttribute("order", orders);

		List<Orders_Details> ordersDetailsList = ordersDetailsDAO.loadOrdersDetailsByOrderId(orderId);
		model.addAttribute("orderDetailList", ordersDetailsList);

		return "back-end/updateOrders";
	}

	@RequestMapping("/updateOrders")
	public String updateOrders(@Valid@ModelAttribute("order")Orders orders,
							   BindingResult result,
							   Model model){
		if (result.hasErrors()){
			model.addAttribute("order", orders);
			model.addAttribute("failed", "Update order is failed!");

			return "back-end/updateOrders";
		}else {
			boolean b = ordersDAO.updateOrders(orders);

			if (b){
				return "redirect:/admin/detailOder?id="+orders.getId();
			}else {
				model.addAttribute("order", orders);
				model.addAttribute("failed", "Update order is failed!");

				return "back-end/updateOrders";
			}
		}
	}

	@RequestMapping("/updateOrderDetail")
	public String updateOrderDetail(@RequestParam("orderDetailId")Integer orderDetailId,
									@RequestParam("quantityOrderDetail")Integer quantity,
									@RequestParam("orderId")Integer orderId,
									Model model){
		if (quantity == null){
			model.addAttribute("failed", "Quantity is cannot null");

			Orders orders = ordersDAO.loadOrderById(orderId);
			model.addAttribute("order", orders);

			List<Orders_Details> ordersDetailsList = ordersDetailsDAO.loadOrdersDetailsByOrderId(orderId);
			model.addAttribute("orderDetailList", ordersDetailsList);

		}else {
			Orders_Details orderDetailById = ordersDetailsDAO.getOrderDetailById(orderDetailId);
			orderDetailById.setQuantity(quantity);
			orderDetailById.setTotalPrice(quantity*orderDetailById.getBooksDetails().getPriceAfterSale());
			boolean b = ordersDetailsDAO.updateOrderDetail(orderDetailById);

			if (!b){
				System.out.println("ERROR");
			}

			model.addAttribute("success", "Update book detail successfully!");

			Orders orders = ordersDAO.loadOrderById(orderId);
			model.addAttribute("order", orders);

			List<Orders_Details> ordersDetailsList = ordersDetailsDAO.loadOrdersDetailsByOrderId(orderId);
			model.addAttribute("orderDetailList", ordersDetailsList);

		}
		return "back-end/updateOrders";
	}

	@RequestMapping("/preUpdateOrderDetail")
	public String preUpdateOrderDetail(@RequestParam("id")Integer id, Model model){
		Orders_Details orderDetailById = ordersDetailsDAO.getOrderDetailById(id);

		model.addAttribute("oderDetail", orderDetailById);
		return "back-end/updateOrdersDetail";
	}

	@RequestMapping("/updateOrdersDetail")
	public String updateOrdersDetail(@ModelAttribute("oderDetail")Orders_Details ordersDetails,
									 Model model){
		if (ordersDetails.getQuantity() == null || ordersDetails.getQuantity() < 1){
			model.addAttribute("failed", "Quantity is cannot null!");

			model.addAttribute("oderDetail", ordersDetails);
			return "back-end/updateOrdersDetail";
		}else {
			Integer quantity = ordersDetails.getQuantity();
			Books_Details booksDetailsById = books_detailsDAO.getBooksDetailsById(ordersDetails.getBooksDetails().getId());
			Orders orders = ordersDAO.loadOrderById(ordersDetails.getOrders().getId());

			ordersDetails.setOrders(orders);
			ordersDetails.setBooksDetails(booksDetailsById);

			ordersDetails.setTotalPrice(quantity*ordersDetails.getBooksDetails().getPriceAfterSale());

			boolean b = ordersDetailsDAO.updateOrderDetail(ordersDetails);

			if (b){
				return "redirect:/admin/preUpdateOrders?id="+ordersDetails.getOrders().getId();
			}else {
				model.addAttribute("failed", "Update failed!");

				model.addAttribute("oderDetail", ordersDetails);
				return "back-end/updateOrdersDetail";
			}
		}
	}

	@RequestMapping("/removeOderDetail")
	public String removeOderDetail(@RequestParam("id")Integer orderDetailId, Model model){
		Orders_Details orderDetailById = ordersDetailsDAO.getOrderDetailById(orderDetailId);

		List<Orders_Details> ordersDetailsList = ordersDetailsDAO.loadOrdersDetailsByOrderId(orderDetailById.getOrders().getId());
		if (ordersDetailsList.size() == 1){
			model.addAttribute("failed", "This order only have 1 order cannot remove!");

			Orders orders = ordersDAO.loadOrderById(orderDetailById.getOrders().getId());
			model.addAttribute("order", orders);

			List<Orders_Details> ordersDetailsList2 = ordersDetailsDAO.loadOrdersDetailsByOrderId(orderDetailById.getOrders().getId());
			model.addAttribute("orderDetailList", ordersDetailsList2);

			return "back-end/updateOrders";
		}else {
			boolean b = ordersDetailsDAO.deleteOrderDetailById(orderDetailId);

			if (b){
				return "redirect:/admin/preUpdateOrders?id="+orderDetailById.getOrders().getId();
			}else {
				model.addAttribute("failed", "Delete failed!");

				Orders orders = ordersDAO.loadOrderById(orderDetailById.getOrders().getId());
				model.addAttribute("order", orders);

				List<Orders_Details> ordersDetailsList2 = ordersDetailsDAO.loadOrdersDetailsByOrderId(orderDetailById.getOrders().getId());
				model.addAttribute("orderDetailList", ordersDetailsList2);

				return "back-end/updateOrders";
			}
		}
	}

	@RequestMapping("/loadBookInStock")
	public String loadBookInStock(Model model){
		List<Books> books = booksDAO.loadAllBooks();
		for (Books b : books){
			Integer totalStock = 0;
			Integer totalSold = 0;

			List<Books_Details> formatsByBookId = books_detailsDAO.getFormatsByBookId(b.getId());
			for (Books_Details bd : formatsByBookId){
				totalStock+=bd.getQuantityStock();
				totalSold+=bd.getQuantitySold();
			}

			b.setTotalStock(totalStock);
			b.setTotalSold(totalSold);

			boolean b1 = booksDAO.updateBook(b);

			if (!b1){
				System.out.println("ERROR");
			}
		}

		model.addAttribute("bookList", books);

		model.addAttribute("bookInStockActive", "active");

		return "back-end/loadBookInStock";
	}

	@RequestMapping("/detailBookInStock")
	public String detailBookInStock(@RequestParam("id")Integer id, Model model){
		List<Books> books = booksDAO.loadAllBooks();
		model.addAttribute("bookList", books);

		List<Books_Details> formatsByBookId = books_detailsDAO.getFormatsByBookId(id);
		model.addAttribute("formatList", formatsByBookId);

		float totalSold = 0, totalStock = 0;
		for (Books_Details bd : formatsByBookId){
			totalSold += bd.getQuantitySold();
			totalStock += bd.getQuantityStock();
		}
		System.out.println(totalSold + " - " + totalStock);

		float percentSold = (totalSold/totalStock)*100;
		float percentStock = 100 - percentSold;


		System.out.println(percentSold + " " + percentStock);

		model.addAttribute("percentSold", percentSold);
		model.addAttribute("percentStock", percentStock);

		model.addAttribute("bookInStockActive", "active");

		return "back-end/loadBookInStockDetail";
	}

	@RequestMapping("/loadRevenueStatistic")
	public String loadRevenueStatistic(@RequestParam(value = "numberOfDate", required = false)Integer numberOfDate,
									   Model model){
		model.addAttribute("revenueStatisticsActive", "active");

		if (numberOfDate == null){
			numberOfDate = 5;
		}

		List<Date> listDate = new ArrayList<>();
		LocalDate currentDate = LocalDate.now();
		for (int i = 0; i <= numberOfDate-1; i++) {
			LocalDate date = currentDate.minusDays(i);

			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

			Date date1;

			try {
				date1 = sf.parse(date.getDayOfMonth()+"/"+date.getMonthValue()+"/"+date.getYear());
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}

			listDate.add(date1);
		}

		model.addAttribute("date", listDate);

		List<Float> totalPriceList = new ArrayList<>();

		for (Date d : listDate){
			List<Orders> ordersList = ordersDAO.loadOrdersByDate(d);

			float totalPrice = 0;

			for (Orders o : ordersList){
				if (o.getStatus() == 1){
					totalPrice += o.getTotalPriceOrder();
				}
			}

			float totalPriceRound = Math.round(totalPrice*100) / 100f;

			totalPriceList.add(totalPriceRound);
		}

		model.addAttribute("totalPriceList", totalPriceList);

		return "back-end/loadStatisticRevenue";
	}

	@RequestMapping("/loadOrdersRevenueWithFilter")
	public String loadRevenueStatisticFilter(@RequestParam("numberOfDate")Integer numberOfDate){
		if (numberOfDate == 5){
			return "redirect:/admin/loadRevenueStatistic?numberOfDate=5";
		} else if (numberOfDate == 10) {
			return "redirect:/admin/loadRevenueStatistic?numberOfDate=10";
		} else if (numberOfDate == 15) {
			return "redirect:/admin/loadRevenueStatistic?numberOfDate=15";
		} else if (numberOfDate == 20) {
			return "redirect:/admin/loadRevenueStatistic?numberOfDate=20";
		} else if (numberOfDate == 25) {
			return "redirect:/admin/loadRevenueStatistic?numberOfDate=25";
		} else if (numberOfDate == 30) {
			return "redirect:/admin/loadRevenueStatistic?numberOfDate=30";
		}else {
			return "redirect:/admin/loadRevenueStatistic";
		}
	}

	@RequestMapping("/banAccUser")
	public String banAccUser(@RequestParam("id")Long id, Model model){
		User userById = userDAO.getUserById(id);

		userById.setEnabled(false);

		boolean b = userDAO.updateUser(userById);

		if (b){
			model.addAttribute("success", "Ban Account is success!");
		}else {
			model.addAttribute("failed", "Ban Account is failed!");
		}

		model.addAttribute("accountManagementActive", "active");
		model.addAttribute("loadAllAccActive", "active");

		model.addAttribute("userById", userById);
		return "back-end/detailUser";
	}

	@RequestMapping("/unBanAccUser")
	public String unBanAccUser(@RequestParam("id")Long id, Model model){
		User userById = userDAO.getUserById(id);

		userById.setEnabled(true);

		boolean b = userDAO.updateUser(userById);

		if (b){
			model.addAttribute("success", "Unban Account is success!");
		}else {
			model.addAttribute("failed", "Unban Account is failed!");
		}

		model.addAttribute("accountManagementActive", "active");
		model.addAttribute("loadAllAccActive", "active");

		model.addAttribute("userById", userById);
		return "back-end/detailUser";
	}

	@RequestMapping("/deleteAccUser")
	public String deleteAccUser(@RequestParam("id")Long id, Model model){
		List<User_Role> userRoles = userRoleDAO.loadAccByUserId(id);
		for (User_Role ur : userRoles){
			boolean b = userRoleDAO.deleteUserRole(ur);

			if (!b){
				System.out.println("ERROR");
			}
		}

		boolean b = userDAO.deleteUser(id);

		if (b){
			model.addAttribute("success", "Delete Account is success!");

			List<User_Role> userRolesList = userRoleDAO.loadAccByRoleId(2L);

			model.addAttribute("listUsers", userRolesList);
		}else {
			model.addAttribute("failed", "Delete Account is failed!");

			List<User_Role> userRolesList = userRoleDAO.loadAccByRoleId(2L);

			model.addAttribute("listUsers", userRolesList);
		}

		model.addAttribute("accountManagementActive", "active");
		model.addAttribute("loadAllAccActive", "active");

		return "back-end/loadAllUsers";
	}

	@RequestMapping("/initAddNewAdminAcc")
	public String initAddNewAdminAcc(Model model){
		User user = new User();

		model.addAttribute("addNewAdminAccActive", "active");
		model.addAttribute("accountManagementActive", "active");

		model.addAttribute("user", user);

		return "back-end/addNewAdminAcc";
	}

	@RequestMapping("/addNewAdminAcc")
	public String addNewAdminAcc(@Valid@ModelAttribute("user")User user,
								 BindingResult result,
								 @RequestParam("confirmPassword")String confirmPassword,
								 Model model){
		model.addAttribute("addNewAdminAccActive", "active");
		model.addAttribute("accountManagementActive", "active");
		if (result.hasErrors()){
			model.addAttribute("failed1", "Add new admin account is failed!");
			model.addAttribute("user", user);

			return "back-end/addNewAdminAcc";
		}else if (user.getUserName() == null || user.getUserName().length() == 0){
			model.addAttribute("user", user);
			model.addAttribute("failedUserName", "Username is cannot empty!");
			return "back-end/addNewAdminAcc";
		}else if (user.getUserName().length() <= 10 && user.getUserName().length() > 0){
			model.addAttribute("user", user);
			model.addAttribute("failedUserName", "Username needs at least 10 characters!");
			return "back-end/addNewAdminAcc";
		}else if (user.getUserName().length() >= 25){
			model.addAttribute("user", user);
			model.addAttribute("failedUserName", "Username maximum 25 characters!");
			return "back-end/addNewAdminAcc";
		}else {
			User checkUser = userDAO.findByUserName(user.getUserName());
			if (checkUser != null) {
				model.addAttribute("user", user);
				model.addAttribute("failed1", "That username is already in use!");
				return "back-end/addNewAdminAcc";
			}

			if(confirmPassword.length() == 0 || user.getPassWord().length() == 0 || user.getPassWord() == null) {
				model.addAttribute("user", user);
				model.addAttribute("failed1", "Password is cannot empty!");
				return "back-end/addNewAdminAcc";
			}else if(!user.getPassWord().equals(confirmPassword)) {
				model.addAttribute("user", user);
				model.addAttribute("failed1", "Password Confirmation doesn't match Password");
				return "back-end/addNewAdminAcc";
			}

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(confirmPassword);
			user.setPassWord(encodedPassword);
			user.setEnabled(true);

			boolean b1 = userDAO.addNewUser(user);

			Role role1 = roleDAO.getRoleByName("ROLE_USER");
			Role role2 = roleDAO.getRoleByName("ROLE_ADMIN");

			User_Role user_Role1 = new User_Role();
			user_Role1.setRole(role1);
			user_Role1.setUser(user);

			boolean b2 = userRoleDAO.addNewUserRole(user_Role1);

			User_Role user_Role2 = new User_Role();
			user_Role2.setRole(role2);
			user_Role2.setUser(user);

			boolean b3 = userRoleDAO.addNewUserRole(user_Role2);

			if(b1&&b2&&b3) {
				User user2 = new User();

				model.addAttribute("addNewAdminAccActive", "active");
				model.addAttribute("accountManagementActive", "active");

				model.addAttribute("success", "Add new admin account successfully!");

				model.addAttribute("user", user2);
			}else {
				model.addAttribute("addNewAdminAccActive", "active");
				model.addAttribute("accountManagementActive", "active");

				model.addAttribute("failed", "Add new admin account failed!");

				model.addAttribute("user", user);
			}
			return "back-end/addNewAdminAcc";
		}
	}

	@RequestMapping("/loadMyAccount")
	public String loadMyAccount(Model model){
		CustomUserDetails user =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User byUserName = userDAO.findByUserName(user.getUsername());

		model.addAttribute("loadMyAccountActive", "active");
		model.addAttribute("user", byUserName);

		return "back-end/viewMyAccount";
	}

	@RequestMapping("/updatePasswordMyAccount")
	public String updatePasswordMyAccount(@RequestParam("oldPassword")String oldPassword,
										  @RequestParam("newPassword")String newPassword,
										  @RequestParam("confirmPassword")String confirmPassword,
										  Model model){
		CustomUserDetails user =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User byUserName = userDAO.findByUserName(user.getUsername());

		if (oldPassword == null || oldPassword.length() == 0){
			model.addAttribute("failedOldPassword", "Old password is cannot be null!");
			model.addAttribute("loadMyAccountActive", "active");
			model.addAttribute("user", byUserName);

			return "back-end/viewMyAccount";
		}else if (newPassword == null || newPassword.length() == 0){
			model.addAttribute("failedNewPassword", "New password is cannot be null!");
			model.addAttribute("loadMyAccountActive", "active");
			model.addAttribute("user", byUserName);

			return "back-end/viewMyAccount";
		}else if(confirmPassword == null || confirmPassword.length() == 0){
			model.addAttribute("failedNewPassword", "Confirm password is cannot be null!");
			model.addAttribute("loadMyAccountActive", "active");
			model.addAttribute("user", byUserName);

			return "back-end/viewMyAccount";
		}else{
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			boolean check = BCrypt.checkpw(oldPassword, byUserName.getPassWord());

			if (!check){
				model.addAttribute("failedOldPassword", "Your old password is not correct, Try again!");
				model.addAttribute("loadMyAccountActive", "active");
				model.addAttribute("user", byUserName);

				return "back-end/viewMyAccount";
			}else if (!confirmPassword.equals(newPassword)){
				model.addAttribute("failedConfirmPassword", "Password Confirmation doesn't match Password, Try again!");
				model.addAttribute("loadMyAccountActive", "active");
				model.addAttribute("user", byUserName);

				return "back-end/viewMyAccount";
			}else {
				String encode = passwordEncoder.encode(confirmPassword);
				byUserName.setPassWord(encode);

				boolean b = userDAO.updateUser(byUserName);

				model.addAttribute("loadMyAccountActive", "active");
				model.addAttribute("user", byUserName);
				if (b){
					model.addAttribute("success", "Update password is successfully!");

				}else{
					model.addAttribute("failed", "Update password is failed!");

				}
				return "back-end/viewMyAccount";
			}
		}
	}

	@RequestMapping("/preUpdateMyAccount")
	public String preUpdateMyAccount(Model model){
		CustomUserDetails user =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User byUserName = userDAO.findByUserName(user.getUsername());

		model.addAttribute("loadMyAccountActive", "active");
		model.addAttribute("user", byUserName);

		return "back-end/updateMyAccount";
	}

	@RequestMapping("/updateMyAccount")
	public String updateMyAccount(@Valid@ModelAttribute("user")User user,
								  BindingResult result,
								  Model model){
		if (result.hasErrors()){
			model.addAttribute("loadMyAccountActive", "active");
			model.addAttribute("user", user);

			return "back-end/updateMyAccount";
		}else {
			boolean b = userDAO.updateUser(user);

			CustomUserDetails user1 =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User byUserName = userDAO.findByUserName(user1.getUsername());

			if (b){
				model.addAttribute("success", "Update information of account successfully!");
			}else{
				model.addAttribute("success", "Update information of account failed!");
			}

			model.addAttribute("loadMyAccountActive", "active");
			model.addAttribute("user", byUserName);
			return "back-end/viewMyAccount";
		}
	}

//////////////////////////
//                      //
//	   					//
//	   MY FUNCTIONAL	//
//						//
//						//
//////////////////////////

	public void changeSalesFailed(Model model, Sales sales){
		model.addAttribute("sale", sales);

		List<Sales> salesList = salesDAO.loadAllSales();
		for (Sales s : salesList){
			List<Books> bookBySaleId = booksDAO.getBookBySaleId(s.getId());
			s.setTotalBook(bookBySaleId.size());
		}

		model.addAttribute("salesList", salesList);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllSalesActive", "active");
	}
	public void changeSalesSuccess(Model model){
		Sales sales = new Sales();
		model.addAttribute("sale", sales);

		List<Sales> salesList = salesDAO.loadAllSales();
		for (Sales s : salesList){
			List<Books> bookBySaleId = booksDAO.getBookBySaleId(s.getId());
			s.setTotalBook(bookBySaleId.size());
		}

		model.addAttribute("salesList", salesList);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllSalesActive", "active");
	}
	public void updateGenreSuccess(Model model){
		Genres genres = new Genres();
		model.addAttribute("genre", genres);

		List<Genres> genresList = genresDAO.loadAllGenres();
		model.addAttribute("genresList", genresList);

		List<Books_Genres> booksGenresList = booksGenresDAO.loadAllBookGenre();

		for (Genres g : genresList){
			int totalBook = 0;

			for (Books_Genres bg : booksGenresList){
				if (bg.getGenres().getId().equals(g.getId())){
					totalBook++;
				}
			}

			g.setTotalBook(totalBook);
			boolean b = genresDAO.updateGenre(g);

			if (!b){
				System.out.println("ERROR");
			}
		}

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllGenreActive", "active");
	}
	public void updateAuthorSuccess(Model model){
		List<Authors> authorsList = authorDAO.loadAllAuthors();
		model.addAttribute("authorList", authorsList);


		List<Books> booksList = booksDAO.loadAllBooks();

		for (Authors a : authorsList){
			int totalBook = 0;

			for (Books b : booksList){
				if (b.getAuthors().getId().equals(a.getId())){
					totalBook++;
				}
			}

			a.setTotalBook(totalBook);
			boolean b = authorDAO.updateAuthor(a);

			if (!b){
				System.out.println("ERROR");
			}
		}

		Authors authors = new Authors();

		model.addAttribute("author", authors);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllAuthorActive", "active");
		model.addAttribute("listAuthorActive", "active");
	}
	public void returnBookDetail(Model model, Books_Details booksDetails){
		Books detailsBookById = booksDAO.getDetailsBookById(booksDetails.getBooks().getId());
		model.addAttribute("b", detailsBookById);

		List<Books_Details> listFormats = books_detailsDAO.getFormatsByBookId(booksDetails.getBooks().getId());
		model.addAttribute("listFormats", listFormats);

		List<Books_Genres> listGenres = booksGenresDAO.loadBooksGenresByBookId(booksDetails.getBooks().getId());
		model.addAttribute("listGenres", listGenres);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllBookActive", "active");
	}
	public void updateBookFormatFailed(Books_Details booksDetails, Model model, String formatName){
		model.addAttribute("book", booksDetails);

		List<Sales> listSales = salesDAO.loadAllSales();
		model.addAttribute("listSales", listSales);

		List<Formats> listFormats = formatsDAO.loadALlFormats();
		model.addAttribute("listFormats", listFormats);

		model.addAttribute("formatName", formatName);
	}
	public void returnDetailBookFormat(Books_Details booksDetails, Model model){
		Books_Details booksDetailsById = books_detailsDAO.getBooksDetailsById(booksDetails.getId());
		model.addAttribute("b", booksDetailsById);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllBookActive", "active");
	}
	public void updateGenreFailed(Genres genres, Model model){
		model.addAttribute("genre", genres);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllGenreActive", "active");
	}
	public void addNewGenreSuccess(Model model){
		Genres genre = new Genres();
		model.addAttribute("genre", genre);

		List<Genres> genresList = genresDAO.loadAllGenres();
		model.addAttribute("genresList", genresList);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllGenreActive", "active");
	}
	public void addNewBookFailed(Books books, Model model){
		model.addAttribute("books", books);

		List<Authors> listAuthor = authorDAO.loadAllAuthors();
		model.addAttribute("listAuthor", listAuthor);

		List<Genres> listGenres = genresDAO.loadAllGenres();
		model.addAttribute("listGenres", listGenres);

		List<Sales> salesList = salesDAO.loadAllSales();
		List<Sales> sales = new ArrayList<>();
		for (Sales s : salesList){
			if (s.getStatus()){
				sales.add(s);
			}
		}
		model.addAttribute("saleList", sales);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllBookActive", "active");
	}
	public void addNewBookSuccess(Model model){
		List<Books> listBooks = booksDAO.loadAllBooks();
		model.addAttribute("listBooks", listBooks);
		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllBookActive", "active");
	}
	public void updateFormatFailed(Formats formats, Model model){
		model.addAttribute("formatById", formats);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllFormatActive", "active");
	}
	public void updateFormatSuccess(Model model){
		Formats formats = new Formats();
		model.addAttribute("format", formats);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllFormatActive", "active");

		List<Formats> listFormats = formatsDAO.loadALlFormats();
		model.addAttribute("listFormats", listFormats);

		List<Books_Details> booksDetailsList = books_detailsDAO.loadAllBookDetail();

		for (Formats f : listFormats){
			int totalBook = 0;

			for (Books_Details bd : booksDetailsList){
				if (bd.getFormats().getId().equals(f.getId())){
					totalBook++;
				}
			}

			f.setTotalBook(totalBook);
			boolean b = formatsDAO.updateFormat(f);

			if (!b){
				System.out.println("ERROR");
			}
		}
	}
	public void addNewFormatFailed(Formats formats, Model model){
		model.addAttribute("format", formats);

		List<Formats> listFormats = formatsDAO.loadALlFormats();
		model.addAttribute("listFormats", listFormats);
		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllFormatActive", "active");
	}
	public void returnAddAndListFormat(Model model){
		Formats formats = new Formats();
		model.addAttribute("format", formats);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllFormatActive", "active");

		List<Formats> listFormats = formatsDAO.loadALlFormats();
		model.addAttribute("listFormats", listFormats);
	}
	public void updateAuthorFailed(Authors authors, Model model){
		model.addAttribute("author", authors);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllAuthorActive", "active");
	}
	public void addNewGenreFailed(Genres genres, Model model){
		model.addAttribute("genre", genres);

		List<Genres> genresList = genresDAO.loadAllGenres();
		model.addAttribute("genresList", genresList);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllGenreActive", "active");
	}
	public void returnAddNewAuthor(Authors authors, Model model){
		List<Authors> authorsList = authorDAO.loadAllAuthors();
		model.addAttribute("authorList", authorsList);

		model.addAttribute("author", authors);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllAuthorActive", "active");
		model.addAttribute("addNewAuthorActive", "active");
	}
	public void returnLoadAllAuthor(Model model, Authors authors){
		List<Authors> authorsList = authorDAO.loadAllAuthors();
		model.addAttribute("authorList", authorsList);

		model.addAttribute("author", authors);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllAuthorActive", "active");
		model.addAttribute("listAuthorActive", "active");
	}
	public void returnPreUpdateBook(Books books, Model model){
		model.addAttribute("book", books);
		if (books.getSales() == null){
			model.addAttribute("saleNull", true);
		}else {
			model.addAttribute("saleName", books.getSales().getName());
		}

		List<Authors> listAuthor = authorDAO.loadAllAuthors();
		model.addAttribute("listAuthor", listAuthor);

		List<Books_Genres> booksGenresList = booksGenresDAO.loadBooksGenresByBookId(books.getId());
		model.addAttribute("booksGenresList", booksGenresList);

		List<Genres> listGenres = genresDAO.loadAllGenres();
		model.addAttribute("listGenres", listGenres);

		List<Sales> salesList = salesDAO.loadAllSales();
		List<Sales> sales = new ArrayList<>();
		for (Sales s : salesList){
			if (s.getStatus()){
				sales.add(s);
			}
		}
		model.addAttribute("listSales", sales);
	}
	public void returnInitAddNewBook(Books books, Model model){
		model.addAttribute("books", books);

		List<Authors> listAuthor = authorDAO.loadAllAuthors();
		model.addAttribute("listAuthor", listAuthor);

		List<Genres> listGenres = genresDAO.loadAllGenres();
		model.addAttribute("listGenres", listGenres);
	}
	public void returnInitAddNewBookDetail(Integer id, Books_Details booksDetails, Model model){
		Books detailsBookById = booksDAO.getDetailsBookById(id);
		model.addAttribute("detailsBookById", detailsBookById);

		model.addAttribute("bookDetails", booksDetails);

		List<Formats> formats = formatsDAO.loadALlFormats();
		model.addAttribute("listFormat", formats);

		List<Sales> sales = salesDAO.loadAllSales();
		model.addAttribute("listSales", sales);

		model.addAttribute("bookManagementActive", "active");
		model.addAttribute("listAllBookActive", "active");
	}
	public boolean checkIsFormatExist(Books_Details booksDetails, Integer id){
		Formats formats = formatsDAO.getFormatById(booksDetails.getFormats().getId());
		String formatName = formats.getName();
		List<Books_Details> formatsByBookId = books_detailsDAO.getFormatsByBookId(id);
		for(Books_Details b : formatsByBookId){
			if (formatName.equalsIgnoreCase(b.getFormats().getName())){
				return true;
			}
		}
		return false;
	}
}
