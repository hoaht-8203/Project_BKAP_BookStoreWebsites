<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
                                  <th scope="col">Product</th>
                                  <th scope="col">Price</th>
                                  <th scope="col">Quantity</th>
                                  <th scope="col">Total</th>
                              </tr>
                          </thead>
                          <tbody>
                              <form:form action="updateCart" method="post">
                                  <c:forEach items="${listBook}" var="bd">
                                      <tr>
                                          <td>
                                              <div class="book-cart">
                                                  <div class="item-3">
                                                      <img src="<c:url value="/resources/images/${bd.bookImg}"/> " alt="">
                                                  </div>
                                                  <div class="item-4">
                                                      <p>${bd.bookName}</p>
                                                  </div>
                                              </div>
                                          </td>
                                          <td>
                                              <h5><fmt:formatNumber value="${bd.bookPrice}" type="currency"/></h5>
                                          </td>
                                          <td>
                                              <div class="product_count">
                                                  <input type="number" name="quantity" maxlength="12" min="1" value="${bd.quantity}" class="input-text qty">
                                                  <input type="hidden" name="bookDetailId" value="${bd.bookDetailId}" class="input-text qty">
                                              </div>
                                          </td>
                                          <td>
                                              <h5><fmt:formatNumber value="${bd.totalPrice}" type="currency"/></h5>
                                          </td>
                                          <td>
                                              <a class="trash-button" href="removeCart?bookId=${bd.bookDetailId}"><span class="bi bi-trash"></span></a>
                                          </td>
                                      </tr>
                                  </c:forEach>
                                  <tr>
                                      <td>
                                          <button class="button">Update Cart</button>
                                      </td>
                                      <td>

                                      </td>
                                      <td>
                                          <h5>Subtotal</h5>
                                      </td>
                                      <td>
                                          <h5>
                                              <fmt:formatNumber value="${subTotal}" type="currency"/>
                                          </h5>
                                      </td>
                                  </tr>
                              </form:form>
                              <tr class="out_button_area">
                                  <td class="d-none-l">

                                  </td>
                                  <td colspan="3">
                                      <c:choose>
                                          <c:when test="${pageContext.request.userPrincipal ne null}">
                                              <div class="checkout_btn_inner d-flex align-items-center justify-content-end">
                                                  <a class="gray_btn" href="#">Continue Shopping</a>
                                                  <a class="primary-btn ml-2" href="user/proceedToCheckout">Proceed to checkout</a>
                                              </div>
                                          </c:when>
                                          <c:otherwise>
                                              <div class="checkout_btn_inner d-flex align-items-center justify-content-end">
                                                  <a class="gray_btn" href="#">Continue Shopping</a>
                                                  <a class="primary-btn ml-2" href="proceedToCheckout">Proceed to checkout</a>
                                              </div>
                                          </c:otherwise>
                                      </c:choose>
                                  </td>
                              </tr>
                          </tbody>
                      </table>
                  </div>
              </div>
          </div>
  </section>
  <!--================End Cart Area =================-->
  
  <jsp:include page="footer.jsp"/>
  <jsp:include page="import_js.jsp"/>
</body>
</html>