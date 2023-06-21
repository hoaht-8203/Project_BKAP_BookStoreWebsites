<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DETAIL DISCOUNT</title>
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
                    </div>
                    <div class="col-12 col-md-6 order-md-2 order-first">
                        <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                            <ol class="breadcrumb">
                                <li class="breadcrumb-item"><a href="">List Discount</a></li>
                                <li class="breadcrumb-item active" aria-current="page">Detail</li>
                            </ol>
                        </nav>
                    </div>
                </div>
            </div>

            <!-- Basic Horizontal form layout section start -->
            <section id="basic-horizontal-layouts">
                <div class="row match-height">
                    <div class="col-md-4 col-12">
                        <section class="section">
                            <div class="card">
                                <div class="card-header">
                                    <h4>Detail Sale (${discount.discount_name})</h4>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-lg">
                                            <tr>
                                                <th>ID</th>
                                                <td>${discount.id}</td>
                                            </tr>
                                            <tr>
                                                <th>Name</th>
                                                <td>${discount.discount_name}</td>
                                            </tr>
                                            <tr>
                                                <th>Discount Code</th>
                                                <td>${discount.discount_code}</td>
                                            </tr>
                                            <tr>
                                                <th>Start Date</th>
                                                <td>${discount.start_date}</td>
                                            </tr>
                                            <tr>
                                                <th>End Date</th>
                                                <td>${discount.end_date}</td>
                                            </tr>
                                            <tr>
                                                <th>Percentage</th>
                                                <td><span class="badge bg-primary">${discount.percent}%</span></td>
                                            </tr>
                                            <tr>
                                                <th>Total Book</th>
                                                <td><span class="badge bg-primary">${discount.totalBook}</span></td>
                                            </tr>
                                            <tr>
                                                <th>Available</th>
                                                <td>
                                                    <c:if test="${discount.available}">
                                                        <span class="badge bg-success">Active</span>
                                                    </c:if>
                                                    <c:if test="${!discount.available}">
                                                        <span class="badge bg-danger">Inactive</span>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                    <div class="col-md-8 col-12">
                        <section class="section">
                            <div class="card">
                                <div class="card-header" style="display: flex;">
                                    <h4 class="col-6">Discount Name: ${discount.discount_name} (all book of this discount)</h4>
                                    <div class="col-6 text-end">
                                        <a class="btn btn-primary" href="initAddNewBookDiscount?discountId=${discount.id}">Add new book</a>
                                        <a class="btn btn-primary" href="initAddNewDiscount">Back</a>
                                    </div>
                                </div>
                                <div class="card-body row">
                                    <table class="table table-hover" id="table1">
                                        <thead>
                                        <tr>
                                            <th>Book ID</th>
                                            <th class="col-2">Image</th>
                                            <th>Title</th>
                                            <th>Sold</th>
                                            <th>Choice</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${listBook}" var="b">
                                                <tr>
                                                    <td>${b.id}</td>
                                                    <td>
                                                        <img style="border-radius: 0.5rem; width: 65%;" src="<c:url value="/resources/images/${b.image}"/>" class="img-fluid" alt="This is a book image">
                                                    </td>
                                                    <td>${b.title}</td>
                                                    <td><span class="badge bg-primary">${b.totalSold}</span></td>
                                                    <td>
                                                        <a data-bs-toggle="tooltip" data-bs-placement="right" class="btn btn-danger rounded-pill" title="DETAIL" href="removeBookDiscount?id=${b.id}"
                                                           onclick="return confirm('Are you sure to remove this book?')">
                                                            <i class="fa fa-trash"></i>
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>
            </section>
            <!-- // Basic Horizontal form layout section end -->
        </div>
    </div>
</div>

<jsp:include page="import_js.jsp"/>
<script>
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

    // Simple Datatable
    let table1 = document.querySelector('#table1');
    let dataTable = new simpleDatatables.DataTable(table1);

    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl)
    })
</script>
</body>
</html>
