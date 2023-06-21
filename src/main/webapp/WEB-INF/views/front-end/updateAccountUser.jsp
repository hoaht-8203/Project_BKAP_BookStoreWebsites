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
                <h2 class="mt-5">Update Account Information</h2>
                <form:form action="updateAccountUser" modelAttribute="user" cssClass="mt-3 col-5">
                    <div class="form-group p-0">
                        <label><b>Full Name</b></label>
                        <form:input type="text" class="form-control" placeholder="Full name" path="fullName"/> <br>
                        <form:errors path="fullName" cssStyle="color: red"/>
                        <form:input path="userName" type="hidden"/>
                        <form:input path="passWord" type="hidden"/>
                        <form:input path="enabled" type="hidden"/>
                        <form:input path="id" type="hidden"/>
                    </div>
                    <div class="form-group p-0">
                        <label><b>Gender</b></label>
                        <form:radiobutton value="true" path="gender" label="Male"/>
                        <form:radiobutton value="false" path="gender" label="Female"/> <br>
                        <form:errors path="gender" cssStyle="color: red"/>
                    </div>
                    <div class="form-group p-0">
                        <label><b>Birthday</b></label>
                        <form:input path="birthday" placeholder="Birthday" type="date" cssClass="form-control"/> <br>
                        <form:errors path="birthday" cssStyle="color: red"/>
                    </div>
                    <div class="form-group p-0">
                        <label><b>Address</b></label>
                        <form:input path="address" placeholder="Address" type="text" cssClass="form-control"/> <br>
                        <form:errors path="address" cssStyle="color: red"/>
                    </div>
                    <div class="form-group p-0">
                        <label><b>Telephone</b></label>
                        <form:input path="telephone" placeholder="Telephone" type="tel" cssClass="form-control"/> <br>
                        <form:errors path="telephone" cssStyle="color: red"/>
                    </div>
                    <div class="form-group p-0">
                        <label><b>Email</b></label>
                        <form:input path="email" placeholder="Email" type="text" cssClass="form-control"/> <br>
                        <form:errors path="email" cssStyle="color: red"/>
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