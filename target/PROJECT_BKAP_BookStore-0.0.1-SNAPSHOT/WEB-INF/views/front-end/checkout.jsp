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

	<!--================Checkout Area =================-->
	<section class="checkout_area section-margin--small">
		<div class="container">
			<div class="row d-flex align-items-start">
				<div class="col-md-4 order-md-2 mb-4">
					<h4 class="d-flex justify-content-between align-items-center mb-3">
						<span class="text-muted">Your cart</span>
						<span class="badge badge-secondary badge-pill">${totalCartBook}</span>
					</h4>
					<ul class="list-group mb-3 sticky-top">
						<c:forEach items="${listBook}" var="bd">
							<li class="list-group-item d-flex justify-content-between lh-condensed">
								<div>
									<h6 class="my-0">${bd.bookName}</h6>
									<small class="text-muted">qty - ${bd.quantity}</small>
								</div>
								<span class="text-muted"><fmt:formatNumber value="${bd.totalPrice}" type="currency"/></span>
							</li>
						</c:forEach>
<%--						<li class="list-group-item d-flex justify-content-between bg-light">--%>
<%--							<div class="text-success">--%>
<%--								<h6 class="my-0">Discount code</h6>--%>
<%--								<small>MKT-103</small>--%>
<%--							</div>--%>
<%--							<span class="text-success">-$5</span>--%>
<%--						</li>--%>
						<li class="list-group-item d-flex justify-content-between">
							<span>Total (USD)</span>
							<strong><fmt:formatNumber value="${subTotal}" type="currency"/></strong>
						</li>
					</ul>
<%--					<form class="card p-2">--%>
<%--						<div class="input-group">--%>
<%--							<input type="text" class="form-control" placeholder="Promo code">--%>
<%--							<div class="input-group-append">--%>
<%--								<button type="submit" class="btn btn-secondary">Redeem</button>--%>
<%--							</div>--%>
<%--						</div>--%>
<%--					</form>--%>
				</div>
				<div class="col-md-8 order-md-1">
					<h4 class="mb-3">Billing address</h4>
					<form:form action="addNewOrder" modelAttribute="order" class="needs-validation" novalidate="">
						<div class="mb-3">
							<form:input type="text" placeholder="Full name" class="form-control" path="fullName"/>
							<form:errors path="fullName" cssClass="text-danger"/>
						</div>
						<div class="mb-3">
							<form:input path="email" type="email" placeholder="Email" class="form-control"/>
							<form:errors path="email" cssClass="text-danger"/>
						</div>
						<div class="mb-3">
							<form:input path="phoneNumber" type="tel" placeholder="Phone"  class="form-control" />
							<form:errors path="phoneNumber" cssClass="text-danger"/>
						</div>
						<div class="mb-3">
							<form:input path="address" type="text" placeholder="Address detail" class="form-control" />
							<form:errors path="address" cssClass="text-danger"/>
						</div>
						<hr class="mb-4">
						<h4 class="mb-3">Payment</h4>
						<div class="d-block my-3">
							<div class="custom-control custom-radio">
								<input id="credit" name="paymentMethod" type="radio" class="custom-control-input" checked="" required="">
								<label class="custom-control-label" for="credit">COD</label>
							</div>
						</div>
						<hr class="mb-4">
						<button class="btn btn-primary btn-lg btn-block" type="submit">Continue to checkout</button>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<!--================End Checkout Area =================-->
	
	<jsp:include page="footer.jsp"></jsp:include>
  <jsp:include page="import_js.jsp"></jsp:include>
</body>
</html>