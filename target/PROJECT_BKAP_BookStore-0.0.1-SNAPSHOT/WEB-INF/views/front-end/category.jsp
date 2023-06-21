<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Category</title>
<%@ include file="import_css.jsp"%>
</head>
<body>
	<!--================ Start Header Menu Area =================-->
	<jsp:include page="header_menu.jsp"/>
	<!--================ End Header Menu Area =================-->
	
	<!--================ Start Header Menu Area =================-->
	<jsp:include page="banner_slide.jsp"/>
	<!--================ End Header Menu Area =================-->
	
	
	<!-- ================ category section start ================= -->
	<section class="section-margin--small mb-5">
		<div class="container-fluid-category">
			<div class="row-category justify-content-center">
				<div class="col-xl-9 col-lg-7 col-md-7">
					<c:choose>
						<c:when test="${searchActive!=null}">
							<h5 style="background-color: #f6f5f7; color: #573ba3" class="mb-3 pb-5 pt-5 pl-4">About ${result} results - If you can't find the book you're looking for, try
								<a href="https://www.google.com/search?q=${searchText} books">searching with Google</a> </h5>
						</c:when>
						<c:otherwise>
							<h1 style="background-color: #f6f5f7; color: #573ba3" class="mb-3 pb-5 pt-5 pl-4">${categoryName}</h1>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="row-category justify-content-center">
				<div class="col-xl-9 col-lg-7 col-md-7">
					<!-- Start Filter Bar -->
					<c:if test="${genre == null }">
						<div class="filter-bar d-flex flex-wrap align-items-center">
							<div class="sorting">
								<c:if test="${genre == null }">
									<form:form id="filterForm" action="filterCategory" method="post">
										<select onchange="this.form.submit()" name="filter" id="filterSelect">
											<option value="none">All</option>
											<option value="priceIncrease">Price Increase</option>
											<option value="priceDecrease">Price Decreasing</option>
										</select>
										<input type="hidden" value="${pageName}" name="pageName">
									</form:form>
								</c:if>
							</div>
						</div>
					</c:if>
					<!-- End Filter Bar -->
					<!-- Start Best Seller -->
					<section class="lattest-product-area pb-40 category-list">
						<div class="row-list-product d-flex align-items-start">
							<c:forEach items="${listBook}" var="bd">
								<div class="col-md-6 col-lg-3">
									<div class="card text-left card-product">
										<div class="card-product__img">
											<a href="detailBook?id=${bd.id}">
												<img class="card-img"
													 src="<c:url value="/resources/images/${bd.books.image}"/>"
													 style="border-radius: 0.7rem"
													 alt="This is image of book">
											</a>
										</div>
										<div class="card-body">
											<a style="margin: 0">${bd.books.authors.fullName}</a>
											<h4 class="card-product__title">
												<a href="detailBook?id=${bd.id}">${bd.books.title}</a>
											</h4>
											<c:if test="${bd.books.sales != null}">
												<p class="card-product__price"><fmt:formatNumber value="${bd.priceAfterSale}" type="currency"/> -
												<b class="text-decoration"><fmt:formatNumber value="${bd.price}" type="currency"/></b> </p>
											</c:if>
											<c:if test="${bd.books.sales == null}">
												<p class="card-product__price"><fmt:formatNumber value="${bd.price}" type="currency"/>
											</c:if>
										</div>
									</div>
								</div>
							</c:forEach>
							<div class="col-md-12 col-lg-12">
								<nav aria-label="Page navigation example">
									<ul class="pagination" style="justify-content: center">
										<c:if test="${newBookActive == 'active'}">
											<c:forEach items="${listPage}" var="page">
												<li class="page-item">
													<c:choose>
														<c:when test="${page.value == 0}">
															<a class="page-link" href="viewAllNewBook?page=${page.key}">${page.key}</a>
														</c:when>
														<c:otherwise>
															<a class="page-link" href="viewAllNewBook?page=${page.key}&isFilter=${page.value}">${page.key}</a>
														</c:otherwise>
													</c:choose>
												</li>
											</c:forEach>
										</c:if>
										<c:if test="${bestSellerActive == 'active'}">
											<c:forEach items="${listPage}" var="page">
												<li class="page-item">
													<c:choose>
														<c:when test="${page.value == 0}">
															<a class="page-link" href="viewAllBestSell?page=${page.key}">${page.key}</a>
														</c:when>
														<c:otherwise>
															<a class="page-link" href="viewAllBestSell?page=${page.key}&isFilter=${page.value}">${page.key}</a>
														</c:otherwise>
													</c:choose>
												</li>
											</c:forEach>
										</c:if>
										<c:if test="${onSaleActive == 'active'}">
											<c:forEach items="${listPage}" var="page">
												<li class="page-item">
													<a class="page-link" href="viewAllOnSaleBook?page=${page}">${page}</a>
												</li>
											</c:forEach>
										</c:if>
										<c:if test="${genreBookActive == 'active'}">
											<c:forEach items="${listPage}" var="page">
												<li class="page-item">
													<a class="page-link" href="viewAllGenreBook?page=${page}">${page}</a>
												</li>
											</c:forEach>
										</c:if>
										<c:if test="${authorBookActive == 'active'}">
											<c:forEach items="${listPage}" var="page">
												<li class="page-item">
													<a class="page-link" href="viewAllGenreBook?page=${page}">${page}</a>
												</li>
											</c:forEach>
										</c:if>
										<c:if test="${findBookActive == 'active'}">
											<c:forEach items="${listPage}" var="page">
												<li class="page-item">
													<a class="page-link" href="searchBook?page=${page}&searchText=${searchText}">${page}</a>
												</li>
											</c:forEach>
										</c:if>
									</ul>
								</nav>
							</div>
						</div>
					</section>
					<!-- End Best Seller -->
				</div>
			</div>
		</div>
	</section>
	<!-- ================ category section end ================= -->
	
	<!--================ End footer Area  =================-->
	<jsp:include page="footer.jsp"/>
	<!-- ================ Footer web ================= --> 
	<jsp:include page="import_js.jsp"/>
	<script>
		// $('filterSelect').change(function (){
		// 	$('filterForm').submit()
		// })
	</script>
</body>
</html>