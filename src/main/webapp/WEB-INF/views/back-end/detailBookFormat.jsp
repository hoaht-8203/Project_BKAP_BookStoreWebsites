<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: hoaht
  Date: 5/5/2023
  Time: 2:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DETAIL BOOK FORMAT</title>
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
                        <h3>${b.books.title}</h3>
                    </div>
                    <div class="col-12 col-md-6 order-md-2 order-first">
                        <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                            <ol class="breadcrumb">
                                <li class="breadcrumb-item"><a href="loadAllBooks">Book Management</a></li>
                                <li class="breadcrumb-item"><a href="loadAllBooks">List All Books</a></li>
                                <li class="breadcrumb-item"><a href="detailBook?id=${b.books.id}">Detail</a></li>
                                <li class="breadcrumb-item active" aria-current="page">${b.formats.name}</li>
                            </ol>
                        </nav>
                    </div>
                </div>
            </div>
            <!-- Basic card section start -->
            <section id="content-types">
                <div class="row">
                    <div class="col-xl-2 col-md-6">
                        <div class="card">
                            <div class="card-content">
                                <img src="<c:url value="/resources/images/${b.books.image}"/>" class="img-fluid" style="border-radius: 0.5rem;" alt="This is book image">
                            </div>
                        </div>
                    </div>

                    <div class="col-xl-10 col-md-6 col-sm-12">
                        <div class="card">
                            <div class="card-content">
                                <div class="card-header">
                                    <div style="display: flex; justify-content: start;">
                                        <a href="detailBook?id=${b.books.id}" style="margin-right: 3px;" class="btn btn-primary rounded-pill">Back</a>
                                        <a href="preUpdateBookFormat?id=${b.id}" style="margin-right: 3px;" class="btn btn-primary rounded-pill">Update</a>
                                        <div class="dropdown" style="margin-right: 3px;">
                                            <button class="btn btn-primary rounded-pill dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                Add New Format
                                            </button>
                                            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton" style="margin: 0px;">
                                                <a class="dropdown-item" href="initAddNewBookDetail?id=${b.books.id}">Add new format to this book</a>
                                                <a class="dropdown-item" href="initAddNewFormat">Add new format to database</a>
                                            </div>
                                        </div>
                                        <a href="deleteBookFormat?id=${b.id}" class="btn btn-danger rounded-pill" onclick="return confirm('ARE YOU SURE TO DELETE THIS FORMAT ?')">Delete</a>
                                    </div>
                                </div>
                                <div class="card-body row">
                                    <div class="col-12 col-md-6">
                                        <div class="card-content">
                                            <div class="card-header">
                                                <h3 class="text-decoration-underline">The information of Books</h3>
                                            </div>
                                            <div class="card-body">
                                                <!-- Table with outer spacing -->
                                                <div class="table-responsive">
                                                    <table class="table table-lg">
                                                        <tr>
                                                            <th>ID</th>
                                                            <td>
                                                                ${b.books.id}
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>Title</th>
                                                            <td>
                                                                ${b.books.title}
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>Create date</th>
                                                            <td>
                                                                <fmt:formatDate value="${b.books.create_date}" type="date" pattern="yyyy-MM-dd"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>Sale</th>
                                                            <td>
                                                                <span class="badge bg-primary">${b.books.sales.name}</span>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-12 col-md-6">
                                        <div class="card-content">
                                            <div class="card-header">
                                                <h3 class="text-decoration-underline">Detail book of ${b.formats.name}</h3>
                                            </div>
                                            <div class="card-body">
                                                <!-- Table with outer spacing -->
                                                <div class="table-responsive">
                                                    <table class="table table-lg">
                                                        <tr>
                                                            <th>Format name</th>
                                                            <td>
                                                                <span class="badge bg-primary">${b.formats.name}</span>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>Publication date</th>
                                                            <td>
                                                                <fmt:formatDate value="${b.publication_date}" type="date" pattern="yyyy-MM-dd"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>Publisher</th>
                                                            <td>
                                                                ${b.publishers}
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>Dimensions</th>
                                                            <td>
                                                                ${b.length} x ${b.width} x ${b.thickness} inches | ${b.weight} pounds
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>Page numbers</th>
                                                            <td>
                                                                ${b.page_number}
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>Language</th>
                                                            <td>
                                                                ${b.language}
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>Price</th>
                                                            <td>
                                                                <fmt:formatNumber value="${b.price}" type="currency"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>Price After Sales</th>
                                                            <td>
                                                                <fmt:formatNumber value="${b.price*(1-b.books.sales.percentage/100)}" type="currency"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>Sold</th>
                                                            <td>
                                                                <span class="badge bg-success">${b.quantitySold}</span>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th>Available</th>
                                                            <td>
                                                                <c:if test="${b.available}">
                                                                    <span class="badge bg-success">Available</span>
                                                                </c:if>

                                                                <c:if test="${!b.available}">
                                                                    <span class="badge bg-danger">Pre Order</span>
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
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
