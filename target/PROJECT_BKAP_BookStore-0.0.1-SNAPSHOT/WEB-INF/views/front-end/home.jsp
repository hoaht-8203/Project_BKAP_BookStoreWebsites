<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <%@ include file="import_css.jsp"%>
</head>
<body>
    <!--================ Start Header Menu Area =================-->
    <jsp:include page="header_menu.jsp"/>
    <!--================ End Header Menu Area =================-->

    <!--================ Start Header Menu Area =================-->
    <jsp:include page="banner_slide.jsp"/>
    <!--================ End Header Menu Area =================-->

    <!-- ================ Newest books ================= -->
    <jsp:include page="newest_books.jsp"/>
    <!-- ================ Best newest book ================= -->

    <!--================ Start best sell =================-->
    <jsp:include page="best_sell.jsp"/>
    <!--================ End best sell =================-->

    <!--================ Start trending book =================-->
    <jsp:include page="onSale.jsp"/>
    <!--================ End trending book =================-->

    <!--================ End footer Area  =================-->
    <jsp:include page="footer.jsp"/>
    <!-- ================ Footer web ================= -->

    <jsp:include page="import_js.jsp"/>
</body>
</html>