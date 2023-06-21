<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="carouselExampleIndicators" class="carousel slide"
		data-ride="carousel">
		<ol class="carousel-indicators">
			<c:forEach var="i" begin="0" end="${saleSize}">
				<c:choose>
					<c:when test="${i==0}">
						<li data-target="#carouselExampleIndicators" data-slide-to="0"
							class="active"></li>
					</c:when>
					<c:otherwise>
						<li data-target="#carouselExampleIndicators" data-slide-to="${i}"></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</ol>
		<div class="carousel-inner">
			<c:forEach items="${salesImg}" var="s">
				<c:choose>
					<c:when test="${firstSale==s.id}">
						<div class="carousel-item active">
							<a href="viewAllBookSale?id=${s.id}"><img class="d-block w-100" src="<c:url value="/resources/images/${s.saleImg}"/>" alt=""></a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="carousel-item">
							<a href="viewAllBookSale?id=${s.id}"><img class="d-block w-100" src="<c:url value="/resources/images/${s.saleImg}"/>" alt=""></a>
						</div>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
		<a class="carousel-control-prev" href="#carouselExampleIndicators"
			role="button" data-slide="prev"> <span
			class="carousel-control-prev-icon" aria-hidden="true"></span> <span
			class="sr-only">Previous</span>
		</a> 
		<a class="carousel-control-next" href="#carouselExampleIndicators"
			role="button" data-slide="next"> <span
			class="carousel-control-next-icon" aria-hidden="true"></span> <span
			class="sr-only">Next</span>
		</a>
	</div>
</body>
</html>