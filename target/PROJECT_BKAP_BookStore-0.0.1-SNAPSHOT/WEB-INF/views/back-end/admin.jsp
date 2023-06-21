<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ADMIN PAGE</title>
	<%@ include file="import_css.jsp"%>
</head>
<body>
	<div class="app">
		<jsp:include page="sidebar.jsp"/>

		<jsp:include page="welcome.jsp"/>
	</div>

	<jsp:include page="import_js.jsp"/>
</body>
</html>