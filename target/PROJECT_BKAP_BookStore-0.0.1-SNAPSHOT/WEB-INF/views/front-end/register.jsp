<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
	<!--================ Start Header Menu Area =================-->
	<jsp:include page="header_menu.jsp"></jsp:include>
	<!--================ End Header Menu Area =================-->

	<!--================Login Box Area =================-->
	<section class="login_box_area section-margin">
		<div class="container">
			<div class="row">
				<div class="col-lg-6">
					<div class="login_box_img">
						<div class="hover">
							<h4>Already have an account?</h4>
							<p>There are advances being made in science and technology everyday, and a good example of this is the</p>
							<a class="button button-account" href="preLogin">Login Now</a>
						</div>
					</div>
				</div>
				<div class="col-lg-6">
					<div class="login_form_inner register_form_inner">
						<h3>Create an account</h3>
						<form:form action="registerUser" cssClass="row login_form" modelAttribute="user" method="post">
							<div class="col-md-12 form-group m-0">
								<form:input path="userName" placeholder="Username" cssClass="form-control"/>
							</div>
							<div class="col-md-12 form-group-right">
								<label><span style="color: red">${failedUserName}</span></label>
							</div>
							<div class="col-md-12 form-group m-0">
								<form:input path="email" type="email" placeholder="Email Address" cssClass="form-control"/>
							</div>
							<div class="col-md-12 form-group-right">
								<label><form:errors path="email"/></label>
							</div>
							<div class="col-md-12 form-group mb-2">
								<input type="password" class="form-control" id="password" name="password" placeholder="Password" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Password'">
							</div>
             				<div class="col-md-12 form-group m-0">
								<input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Confirm Password'">
							</div>
							<div class="col-md-12 form-group-right">
								<label>${failed }</label>
								<label>${success }</label>
							</div>
							<div class="col-md-12 form-group">
								<button type="submit" value="submit" class="button button-register w-100">Continue</button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!--================End Login Box Area =================-->
	
	<!--================ End footer Area  =================-->
	<jsp:include page="footer.jsp"></jsp:include>
	<!-- ================ Footer web ================= --> 
	
	<jsp:include page="import_js.jsp"></jsp:include>
</body>
</html>