<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
	<header class="header_area">
		<div class="main_menu">
			<nav class="navbar navbar-expand-lg navbar-light">
				<div class="container-fluid">
					<a class="navbar-brand logo_h" href="home">
						<img src="<c:url value="/resources/images/logo3.png"/>" alt="">
					</a>
					<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
						<span class="icon-bar"></span> <span class="icon-bar"></span> 
						<span class="icon-bar"></span>
					</button>
					<div class="collapse navbar-collapse offset" id="navbarSupportedContent">
						<ul class="nav navbar-nav menu_nav ml-auto mr-auto">
							<li class="nav-item ${home}"><a class="nav-link" href="home">Home</a></li>
							<li class="nav-item ${newBookActive}"><a class="nav-link" href="viewAllNewBook">New Book</a></li>
							<li class="nav-item ${bestSellerActive}"><a class="nav-link" href="viewAllBestSell">Best Seller</a></li>
							<li class="nav-item ${onSaleActive}"><a class="nav-link" href="viewAllOnSaleBook">On Sale</a></li>
							<li class="nav-item ${kidActive}"><a class="nav-link" href="viewAllGenreBook?id=61">Kids</a></li>
							<li class="nav-item ${mysteriesActive}"><a class="nav-link" href="viewAllGenreBook?id=82">Mysteries</a></li>
							<li class="nav-item ${horrorActive}"><a class="nav-link" href="viewAllGenreBook?id=83">Horror</a></li>
							<li class="nav-item ${cookingActive}"><a class="nav-link" href="viewAllGenreBook?id=81">Cooking & Food Books</a></li>
						</ul>

						<ul class="nav-shop">
								<c:choose>
									<c:when test="${pageContext.request.userPrincipal ne null}">
										<li class="nav-item">
											<!-- <button><i class="bi bi-person"></i></button> -->
											<div class="dropdown">
												<button id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
													<i class="bi bi-person"></i>
												</button>
												<div class="dropdown-menu" aria-labelledby="dropdownMenu2">
													<a href="viewMyAccount"><button class="dropdown-item">My Account</button></a>
													<a href="viewMyWishlist"><button class="dropdown-item">My Wishlists</button></a>
													<form action="<c:url value="/j_spring_security_logout" />" method="post">
														<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
														<button type="submit" class="dropdown-item">Logout</button>
													</form>
												</div>
											</div>
										</li>
									</c:when>
									<c:otherwise>
										<li class="nav-item">
											<div class="dropdown">
												<button id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
													<i class="bi bi-person"></i>
												</button>
												<div class="dropdown-menu" aria-labelledby="dropdownMenu2">
													<a href="preLogin" class="dropdown-item">Login</a>
													<a href="preRegistration" class="dropdown-item">Register</a>
												</div>
											</div>
										</li>
									</c:otherwise>
								</c:choose>
							<li class="nav-item">
								<a href="viewCart"><button>
									<i class="ti-shopping-cart"></i><span class="nav-shop__circle">${totalCartBook}</span>
								</button></a>
							</li>
							<li class="nav-item">
								<div class="input-group mb-3">
									<form action="searchBook" method="get">
										<input type="text" value="${searchText}" name="searchText" class="form-control" placeholder="Search by title, author, genre"/>
									</form>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</nav>
		</div>
	</header>
</body>
</html>