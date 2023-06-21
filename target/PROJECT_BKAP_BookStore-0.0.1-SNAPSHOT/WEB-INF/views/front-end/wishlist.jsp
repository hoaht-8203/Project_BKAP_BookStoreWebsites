<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="import_css.jsp"%>
</head>
<body>
	<jsp:include page="header_menu.jsp"></jsp:include>
	
	<section class="cart_area">
          <div class="container">
              <div class="cart_inner">
                  <div class="table-responsive">
                      <table class="table">
                          <thead>
                              <tr>
                                  <th scope="col">Book</th>
                                  <th scope="col" colspan="2">Format Name</th>
                                  <th scope="col">Price</th>
                                  <th scope="col"></th>
                                  <th scope="col"></th>
                              </tr>
                          </thead>
                          <tbody>
                              <c:forEach items="${wishlists}" var="w">
                                  <tr>
                                      <td>
                                          <div class="book-cart">
                                              <div class="item-3">
                                                  <a href="detailBook?id=${w.booksDetails.id}"><img src="<c:url value="/resources/images/${w.booksDetails.books.image}"/> " alt=""></a>
                                              </div>
                                              <div class="item-4">
                                                  <a href="detailBook?id=${w.booksDetails.id}">
                                                      <p style="color: #2c293b !important">${w.booksDetails.books.title}</p>
                                                  </a>
                                              </div>
                                          </div>
                                      </td>
                                      <td>
                                          <p>${w.booksDetails.formats.name}</p>
                                      </td>
                                      <td>

                                      </td>
                                      <td>
                                          <h5><fmt:formatNumber value="${w.booksDetails.priceAfterSale}" type="currency"/></h5>
                                      </td>
                                      <td colspan="3">
                                          <div class="d-flex align-items-center">
                                              <a class="button-danger primary-btn text-center mr-4" href="addBookToCart?bookDetailId=${w.booksDetails.id}">Buy</a>
                                              <a class="trash-button" onclick="return confirm('Are you sure?')" href="removeBookWishlist?id=${w.id}"><span class="bi bi-trash"></span></a>
                                          </div>
                                      </td>
                                  </tr>
                              </c:forEach>
                          </tbody>
                      </table>
                  </div>
              </div>
          </div>
  </section>
  <!--================End Cart Area =================-->
  
  <jsp:include page="footer.jsp"></jsp:include>
  <jsp:include page="import_js.jsp"></jsp:include>
</body>
</html>