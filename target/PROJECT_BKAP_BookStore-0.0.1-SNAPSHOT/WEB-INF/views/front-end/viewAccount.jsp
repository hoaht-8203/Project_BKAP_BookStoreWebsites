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
	<jsp:include page="header_menu.jsp"/>

    <section class="cart_area">
        <div class="container">
            <div class="cart_inner">
                <h2>1. My Account</h2>
                <p class="m-0 mt-3"><b>Full Name</b>: ${user.fullName}</p>
                <p class="m-0"><b>Gender</b>: ${user.gender?"Male":"Female"}</p>
                <p class="m-0"><b>Birthday</b>: <fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/></p>
                <p class="m-0"><b>Address</b>: ${user.address}</p>
                <p class="m-0"><b>Telephone</b>: ${user.telephone}</p>
                <p class="m-0"><b>Email</b>: ${user.email}</p>
                <a href="preUpdateAccountUser" style="color: red">(Update Information)</a>

                <h2 class="mt-5">2. Update password</h2>
                <form:form action="updatePasswordUser" cssClass="mt-3 col-5">
                    <div class="form-group p-0">
                        <label><b>Old Password</b></label>
                        <input name="oldPassword" type="password" class="form-control" placeholder="Old Password">
                    </div>
                    <div class="form-group p-0">
                        <label><b>New Password</b></label>
                        <input name="newPassword" type="password" class="form-control" placeholder="New Password">
                    </div>
                    <div class="form-group p-0">
                        <label><b>Confirm Password</b></label>
                        <input name="confirmPassword" type="password" class="form-control" placeholder="Confirm Password">
                    </div>
                    <button type="submit" class="btn btn-primary">Update</button>
                    <p class="mt-2" style="color: red">${failed}</p>
                    <p class="mt-2" style="color: green">${success}</p>
                </form:form>
            </div>
        </div>
    </section>
  <!--================End Cart Area =================-->
  
  <jsp:include page="footer.jsp"/>
  <jsp:include page="import_js.jsp"/>
</body>
</html>