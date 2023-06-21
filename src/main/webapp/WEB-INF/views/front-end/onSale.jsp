<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>On Sale</title>
</head>
<body>
	<section class="section-margin calc-60px">
		<div class="container">
			<div class="row justify-content-between section-intro pb-60px">
				<h2 class="col-9">
					On <span class="section-intro__style">Sale Books</span>
				</h2>
				<a class="col-3 right_buttom button button-header" href="viewAllOnSaleBook">View all (${countOnSaleBook	})</a>
			</div>
			<div class="owl-carousel owl-theme d-flex align-items-start" id="bestSellerCarousel">
				<c:forEach items="${listOnSaleBook}" var="bd">
					<div class="card">
						<a href="detailBook?id=${bd.id}">
							<img style="border-radius: 0.7rem" src="<c:url value="/resources/images/${bd.books.image}"/>" class="card-img-top" alt="...">
						</a>
						<div class="card-body">
							<a href="detailBook?id=${bd.id}"><h5 class="">${bd.books.title}</h5></a><br>
							<a href="#">${bd.books.authors.fullName}</a>
							<p class="card-product__price">
								<fmt:formatNumber value="${bd.priceAfterSale}" type="currency"/>
								-<b class="text-decoration"><fmt:formatNumber value="${bd.price}" type="currency"/></b> </p>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</section>
</body>
</html>