<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sprng" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UPDATE BOOK</title>
    <%@ include file="import_css.jsp" %>
</head>
<body>
<div class="app">
    <jsp:include page="sidebar.jsp"/>

    <div id="main">
        <header class="mb-3">
            <a href="#" class="burger-btn d-block d-xl-none">
                <i class="bi bi-justify fs-3"></i>
            </a>
        </header>

        <div class="page-heading">
            <div class="page-title">
                <div class="row">
                    <div class="col-12 col-md-6 order-md-1 order-last">
                        <h3>Update Book</h3>
                    </div>
                    <div class="col-12 col-md-6 order-md-2 order-first">
                        <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                            <ol class="breadcrumb">
                                <li class="breadcrumb-item"><a href="#">Book Management</a></li>
                                <li class="breadcrumb-item"><a href="#">List All Books</a></li>
                                <li class="breadcrumb-item active" aria-current="page">Update</li>
                            </ol>
                        </nav>
                    </div>
                </div>
            </div>
            <!-- Basic card section start -->
            <section id="content-types">
                <div class="row">
                    <div class="col-xl-9 col-md-6 col-sm-12">
                        <section class="section">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Please Enter The Information</h4>
                                </div>
                                <div class="card-content">
                                    <div class="card-body">
                                        <spring:form action="updateBook" cssClass="form form-horizontal" method="post" modelAttribute="book" enctype="multipart/form-data">
                                            <div class="form-body">
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <label>Book ID</label>
                                                    </div>
                                                    <div class="col-md-9 form-group">
                                                        <spring:input path="id" cssClass="form-control mb-2" readonly="true"/>
                                                        <spring:input type="hidden" path="totalFormat" cssClass="form-control mb-2" readonly="true"/>
                                                        <spring:input type="hidden" path="totalSold" cssClass="form-control mb-2" readonly="true"/>
                                                        <spring:input type="hidden" path="totalStock" cssClass="form-control mb-2" readonly="true"/>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label>Create Date</label>
                                                    </div>
                                                    <div class="col-md-9 form-group">
                                                        <spring:input path="create_date" cssClass="form-control mb-2" readonly="true"/>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label>Book Title</label>
                                                    </div>
                                                    <div class="col-md-9 form-group">
                                                        <spring:input path="title" cssClass="form-control mb-2"/>
                                                        <spring:errors path="title" cssClass="alert alert-danger p-1 text-center"/>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label>Book Author</label>
                                                    </div>
                                                    <div class="col-md-9 form-group">
                                                        <spring:select cssClass="form-select mb-2" path="authors.id">
                                                            <c:if test="${authorId == book.authors.id}">
                                                                <spring:option value="${authorId}" label="${book.authors.fullName}"/>
                                                            </c:if>

                                                            <c:forEach items="${listAuthor}" var="a">
                                                                <spring:option value="${a.id}" label="${a.fullName}"/>
                                                            </c:forEach>
                                                        </spring:select>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label>Sales</label>
                                                    </div>
                                                    <div class="col-md-9 form-group">
                                                        <spring:select cssClass="form-select" path="sales.id">
                                                            <c:if test="${saleNull}">
                                                                <spring:option value="0" label="Not Sales"/>
                                                            </c:if>
                                                            <c:if test="${!saleNull}">
                                                                <spring:option value="${book.sales.id}" label="${saleName}"/>
                                                                <spring:option value="0" label="Not Sales"/>
                                                            </c:if>
                                                            <c:forEach items="${listSales}" var="s">
                                                                <c:if test="${saleName != s.name}">
                                                                    <spring:option value="${s.id}" label="${s.name}"/>
                                                                </c:if>
                                                            </c:forEach>
                                                        </spring:select>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label>Book Genres</label>
                                                    </div>
                                                    <div class="col-md-9 form-group">
                                                        <select class="choices form-select multiple-remove" name="selectGenres" multiple="multiple">
                                                            <c:forEach var="g" items="${listGenres}">
                                                                <c:set var="isSelected" value="false" />
                                                                <c:forEach var="bg" items="${booksGenresList}">
                                                                    <c:if test="${g.id == bg.genres.id}">
                                                                        <c:set var="isSelected" value="true" />
                                                                    </c:if>
                                                                </c:forEach>
                                                                <c:choose>
                                                                    <c:when test="${isSelected}">
                                                                        <option value="${g.id}" selected>${g.name}</option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <option value="${g.id}">${g.name}</option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </select>
                                                        <c:if test="${errorGenre}">
                                                            <span class="alert alert-danger p-1 text-center">Genre is cannot empty!</span>
                                                        </c:if>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label>Description Title</label>
                                                    </div>
                                                    <div class="col-md-9 form-group">
                                                        <spring:textarea path="description_title" cssClass="form-control mb-2" cols="85" rows="4"/>
                                                        <spring:errors path="description_title" cssClass="alert alert-danger p-1 text-center"/>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label>Description Detail</label>
                                                    </div>
                                                    <div class="col-md-9 form-group">
                                                        <spring:textarea path="description_detail" cssClass="form-control mb-2" cols="85" rows="7"/>
                                                        <spring:errors path="description_detail" cssClass="alert alert-danger p-1 text-center"/>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label>Book Image</label>
                                                    </div>
                                                    <div class="col-md-9 form-group">
                                                        <input name="bookImg" type="file" class="form-control" required="required">
                                                    </div>
                                                    <div class="col-sm-12 d-flex justify-content-start">
                                                        <button type="submit" class="btn btn-primary me-1 mb-1">Update
                                                        </button>
                                                        <a href="loadAllBooks" class="btn btn-light-secondary me-1 mb-1">Cancel</a>
                                                        <button type="reset" class="btn btn-light-secondary me-1 mb-1">
                                                            Reset
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </spring:form>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>
            </section>
            <!-- Basic Card types section end -->
        </div>
    </div>

    <jsp:include page="import_js.jsp"/>

        <script>
            var failed = "${failed}";
            if (failed){
                Toastify({
                    text: failed,
                    duration: 3000,
                    close:true,
                    gravity:"top",
                    position: "center",
                    backgroundColor: "#dc3545",
                }).showToast();
            }

            var success = "${success}"
            if (success){
                Toastify({
                    text: success,
                    duration: 3000,
                    close:true,
                    gravity:"top",
                    position: "center",
                    backgroundColor: "#4caf50",
                }).showToast();
            }
        </script>
</body>
</html>
