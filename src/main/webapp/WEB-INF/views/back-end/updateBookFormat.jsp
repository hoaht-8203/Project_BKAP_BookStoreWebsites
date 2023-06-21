<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UPDATE BOOK FORMAT</title>
    <%@ include file="import_css.jsp"%>
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
                        <h3>${book.books.title}</h3>
                    </div>
                    <div class="col-12 col-md-6 order-md-2 order-first">
                        <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                            <ol class="breadcrumb">
                                <li class="breadcrumb-item"><a href="#">Book Management</a></li>
                                <li class="breadcrumb-item"><a href="#">List All Books</a></li>
                                <li class="breadcrumb-item active" aria-current="page">Update Book Format</li>
                            </ol>
                        </nav>
                    </div>
                </div>
            </div>
            <!-- Basic card section start -->
            <section id="content-types">
                <div class="row">
                    <div class="col-xl-3 col-md-6 col-sm-12">
                        <div class="card">
                            <div class="card-content">
                                <img src="<c:url value="/resources/images/${book.books.image}"/>" class="card-img-top img-fluid" alt="This is a book image">
                            </div>
                        </div>
                    </div>

                    <div class="col-xl-9 col-md-6 col-sm-12">
                        <section class="section">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Please Enter The Information</h4>
                                </div>
                                <div class="card-content">
                                    <div class="card-body">
                                        <spring:form action="updateBookFormat" cssClass="form form-horizontal" method="post" modelAttribute="book">
                                            <div class="form-body">
                                                <div class="row">
                                                    <div class="col-md-2">
                                                        <label>Book ID</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <spring:input path="books.id" class="form-control" readonly="true"/>
                                                        <spring:input path="id" type="hidden" cssClass="form-control"/>
                                                        <spring:input path="quantitySold" type="hidden" cssClass="form-control"/>
                                                        <spring:input path="quantityStock" type="hidden" cssClass="form-control"/>
                                                        <spring:input path="priceAfterSale" type="hidden" cssClass="form-control"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Book Image</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <spring:input path="books.image" class="form-control" readonly="true"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Book Title</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <spring:input path="books.title" cssClass="form-control" readonly="true"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Total Sold</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <spring:input path="quantitySold" cssClass="form-control" readonly="true"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Format Name</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <spring:select cssClass="form-select mb-2" path="formats.id">
                                                            <c:forEach items="${listFormats}" var="f">
                                                                <c:choose>
                                                                    <c:when test="${f.name == formatName}">
                                                                        <spring:option value="${f.id}" selected="true">${f.name}</spring:option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <spring:option value="${f.id}">${f.name}</spring:option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </spring:select>
                                                        <input type="hidden" name="formatName" value="${formatName}">
                                                        <c:if test="${errorFormat}">
                                                            <span class="alert alert-danger p-1 text-center">This format is already be used!</span>
                                                        </c:if>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Publication Date</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <spring:input path="publication_date" cssClass="form-control mb-2" type="date"/>
                                                        <spring:errors cssClass="alert alert-danger p-1 text-center" path="publication_date"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Publisher</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <spring:input path="publishers" cssClass="form-control mb-2"/>
                                                        <spring:errors cssClass="alert alert-danger p-1 text-center" path="publishers"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Language</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <spring:input path="language" cssClass="form-control mb-2"/>
                                                        <spring:errors cssClass="alert alert-danger p-1 text-center" path="language"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Page Number</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <spring:input path="page_number" type="number" cssClass="form-control mb-2"/>
                                                        <spring:errors cssClass="alert alert-danger p-1 text-center" path="page_number"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Length</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <spring:input path="length" type="number" step="any" cssClass="form-control mb-2"/>
                                                        <spring:errors cssClass="alert alert-danger p-1 text-center" path="length"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Width</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <spring:input path="width" type="number" step="any" cssClass="form-control mb-2"/>
                                                        <spring:errors cssClass="alert alert-danger p-1 text-center" path="width"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Thickness</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <spring:input path="thickness" type="number" step="any" cssClass="form-control mb-2"/>
                                                        <spring:errors cssClass="alert alert-danger p-1 text-center" path="thickness"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Weight</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <spring:input path="weight" type="number" step="any" cssClass="form-control mb-2"/>
                                                        <spring:errors cssClass="alert alert-danger p-1 text-center" path="weight"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Price</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <spring:input path="price" type="number" step="any" cssClass="form-control mb-2"/>
                                                        <spring:errors cssClass="alert alert-danger p-1 text-center" path="price"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Available</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <spring:radiobutton path="available" cssClass="form-check-input" value="true" label="Available"/>
                                                        <spring:radiobutton path="available" cssClass="form-check-input" value="false" label="Pre Order"/> <br>
                                                        <spring:errors cssClass="alert alert-danger mt-2 p-1 text-center" path="available"/>
                                                    </div>
                                                    <div class="col-sm-12 d-flex justify-content-start">
                                                        <button type="submit" class="btn btn-primary me-1 mb-1">Update</button>
                                                        <a href="detailBook?id=${book.books.id}" class="btn btn-light-secondary me-1 mb-1">Cancel</a>
                                                        <button type="reset" class="btn btn-light-secondary me-1 mb-1">Reset</button>
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

        document.getElementById('showMyAlert').addEventListener('click', () => {
            Toastify({
                text: "You need to choose format before update",
                duration: 3000,
                close:true,
                gravity:"top",
                position: "center",
                backgroundColor: "#dc3545",
            }).showToast();
        })
    </script>
</body>
</html>
