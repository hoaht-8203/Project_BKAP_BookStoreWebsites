<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="import_css.jsp"%>
<link href="<c:url value="/resources/theme-back-end/assets/vendors/fontawesome/all.min.css" />" rel="stylesheet">
	<style>
		.genreLink {
			color: black;
		}

		.genreLink:hover{
			color: black;
			text-decoration: underline;
		}
	</style>
</head>
<body>
	<!--================ Start Header Menu Area =================-->
	<jsp:include page="header_menu.jsp"/>
	<!--================ End Header Menu Area =================-->
	
	<!--================ Start detail book=================-->
	<div class="product_image_area">
		<div class="container">
			<div class="detail-book">
				<div></div>
				<div class="item-1">
					<div class="">
						<div class="single-prd-item">
							<img style="border-radius: 0.7rem" class="img-fluid" src="<c:url value="/resources/images/${bookDetail.books.image}"/>" alt="">
						</div>
					</div>
				</div>    
				<div class="item-2">
					<div class="s_product_text">
						<h3>${bookDetail.books.title}</h3>
						
						<a href="viewAllBookByAuthorId?authorId=${bookDetail.books.authors.id}" class="author-name">${bookDetail.books.authors.fullName}</a>
						
						<div class="card-deck">
							<c:forEach items="${listFormat}" var="f">
								<c:choose>
									<c:when test="${f.formats.id == bookDetail.formats.id}">
										<a href="detailBook?id=${f.id}" class="card-format selected">
											<div class="card-body">
												<h5 class="card-title">${f.formats.name}</h5>
												<p class="card-text">${f.language}</p>
												<c:if test="${f.books.sales != null}">
													<p style="font-size: larger">
														<b class="old-price"><fmt:formatNumber value="${f.price}" type="currency"/></b>
														<fmt:formatNumber value="${f.priceAfterSale}" type="currency"/>
													</p>
												</c:if>
												<c:if test="${f.books.sales == null}">
													<p style="font-size: larger">
														<fmt:formatNumber value="${f.price}" type="currency"/>
													</p>
												</c:if>
											</div>
										</a>
									</c:when>
									<c:otherwise>
										<a href="detailBook?id=${f.id}" class="card-format">
											<div class="card-body">
												<h5 class="card-title">${f.formats.name}</h5>
												<p class="card-text">${f.language}</p>
												<c:if test="${f.books.sales != null}">
													<p style="font-size: larger">
														<b class="old-price"><fmt:formatNumber value="${f.price}" type="currency"/></b>
														<fmt:formatNumber value="${f.priceAfterSale}" type="currency"/>
													</p>
												</c:if>
												<c:if test="${f.books.sales == null}">
													<p style="font-size: larger">
														<fmt:formatNumber value="${f.price}" type="currency"/>
													</p>
												</c:if>
											</div>
										</a>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</div>

						<ul class="list">
							<c:if test="${bookDetail.available}">
								<li>
									<span class="d-inline-flex align-items-center">
										<i style="color: #1fa5a3" class="fa fa-check-circle"></i>
										<span class="pl-2 available">AVAILABLE</span>
									</span>
								</li>
							</c:if>
							<c:if test="${!bookDetail.available}">
								<li>
									<span class="d-inline-flex align-items-center">
										<i style="color: #1fa5a3" class="fa fa-plus-circle"></i>
										<span class="pl-2 available">PRE-ORDER</span>
									</span>
								</li>
							</c:if>
						</ul>
						<div class="product_count">
							<c:if test="${bookDetail.available}">
								<a class="button-danger primary-btn" href="addBookToCart?bookDetailId=${bookDetail.id}">ADD TO CART</a>
							</c:if>
							<c:if test="${!bookDetail.available}">
								<a class="button-danger primary-btn" href="addBookToCart?bookDetailId=${bookDetail.id}">PRE-ORDER</a>
							</c:if>
							<a class="button button-header-2" href="user/addNewWishlist?bookDetailId=${bookDetail.id}">ADD TO WISHLIST</a>
						</div>
						<div>
							<h3>Description</h3>
							<p><strong>${bookDetail.books.description_title}</strong></p>
							<p>${bookDetail.books.description_detail}</p>
							<h3>Product Details</h3>
							<div class="product-detail" style="width: 60%">
								<div class="col-4" style="text-align: right;">Price</div>
								<div class="col-8 money">
									<b class="old-price">
										<fmt:formatNumber value="${bookDetail.price}" type="currency"/>
									</b>
									<fmt:formatNumber value="${bookDetail.priceAfterSale}" type="currency"/>
								</div>

								<div class="col-4" style="text-align: right;">Publisher</div>
								<div class="col-8"><b>${bookDetail.publishers}</b></div>

								<div class="col-4" style="text-align: right;">Publish <Date></Date></div>
								<div class="col-8"><b><fmt:formatDate value="${bookDetail.publication_date}" type="date" pattern="yyyy-MM-dd"/></b></div>

								<div class="col-4" style="text-align: right;">Pages</div>
								<div class="col-8"><b>${bookDetail.page_number}</b></div>

								<div class="col-4" style="text-align: right;">Dimensions</div>
								<div class="col-8"><b>${bookDetail.width} X ${bookDetail.length} X ${bookDetail.thickness} inches | ${bookDetail.weight} pounds</b></div>
								
								<div class="col-4" style="text-align: right;">Language</div>
								<div class="col-8"><b>${bookDetail.language}</b></div>

								<div class="col-4" style="text-align: right;">Type</div>
								<div class="col-8"><b>${bookDetail.formats.name}</b></div>

								<div class="col-4" style="text-align: right;">Genres</div>
								<div class="col-8"><b>
									<c:forEach items="${bookGenre}" var="g">
										<a href="viewAllGenreBook?id=${g.genres.id}" class="genreLink">${g.genres.name}</a>,
									</c:forEach>
								</b></div>
							</div>
<%--							<h3>Reviews</h3>--%>
<%--							<p><i>"In Your Super Life, Michael and Kristel share the --%>
<%--							fruits of their journey by showing you how to eat in a new way. --%>
<%--							They describe easy steps for changing daily habits that will help your sense --%>
<%--							of wellbeing. Just as I believe, the path towards better health should focus --%>
<%--							on what to add to your diet, and not just elimination."</i>--%>
<%--							<strong>-- from the foreword by William W. Li, MD, New York Times bestselling author of Eat to Beat Disease(reference)</strong></p>--%>
<%--							--%>
<%--							<p><i>"Food is medicine, and we now know definitively that plant-based --%>
<%--							nutrition has immense healing potential as it combats inflammation, which --%>
<%--							causes over 80% of chronic disease. Kristel and Michael's book has so many --%>
<%--							clean, plant-based, superfood recipes that will help more people benefit from --%>
<%--							the healing power of plants, and I am excited to see the impact this will have--%>
<%--							 on so many people."</i>--%>
<%--							<strong>-- from the foreword by William W. Li, MD, New York Times bestselling author of Eat to Beat Disease(reference)</strong></p>--%>
<%--							--%>
<%--							<p><i>"In Your Super Life, Michael and Kristel share the --%>
<%--							fruits of their journey by showing you how to eat in a new way. --%>
<%--							They describe easy steps for changing daily habits that will help your sense --%>
<%--							of wellbeing. Just as I believe, the path towards better health should focus --%>
<%--							on what to add to your diet, and not just elimination."</i>--%>
<%--							<strong>-- Deepak Chopra, MD, New York Times bestselling author(reference)</strong></p>--%>
							<h3 class="mt-3">About the Author</h3>
							<p>
								${bookDetail.books.authors.description}
							</p>
						</div>
					</div>
				</div> 
			</div>
			<hr>
		</div>
	</div>
	<!--================ End detail book  =================-->

	<!--================ Start related book  =================-->
<%--	<jsp:include page="related_books.jsp"></jsp:include>--%>
	<!--================ End related book  =================-->
	
	
	<!--================ End footer Area  =================-->
	<jsp:include page="footer.jsp"/>
	<!-- ================ Footer web ================= --> 
	<jsp:include page="import_js.jsp"/>
	<script src="<c:url value="/resources/theme-back-end/assets/vendors/fontawesome/all.min.js" />"></script>
</body>
</html>