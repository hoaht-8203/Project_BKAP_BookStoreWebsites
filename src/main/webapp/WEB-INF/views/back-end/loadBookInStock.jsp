<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ADD AND LIST FORMAT</title>
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
                            <h3>Book In Stock</h3>
                        </div>
                        <div class="col-12 col-md-6 order-md-2 order-first">
                            <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item active" aria-current="page">Book In Stock</li>
                                </ol>
                            </nav>
                        </div>
                    </div>
                </div>

                <!-- Basic Horizontal form layout section start -->
                <section id="basic-horizontal-layouts">
                    <div class="row match-height">
                        <div class="col-md-6 col-12">
                            <section class="section">
                                <div class="card">
                                    <div class="card-header">
                                        List All Books
                                    </div>
                                    <div class="card-body">
                                        <table class="table table-hover" id="table1">
                                            <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th class="col-2">Image</th>
                                                <th>Title</th>
                                                <th>Total Sold</th>
                                                <th>Total Stock</th>
                                                <th>Total Format</th>
                                                <th>Choice</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${bookList}" var="b">
                                                <tr>
                                                    <td>${b.id}</td>
                                                    <td>
                                                        <img src="<c:url value="/resources/images/${b.image}"/>" style="border-radius: 0.5rem;" class="img-fluid" alt="This is a book image">
                                                    </td>
                                                    <td>
                                                        ${b.title}
                                                    </td>
                                                    <td><span class="badge bg-primary">${b.totalSold}</span></td>
                                                    <td><span class="badge bg-primary">${b.totalStock}</span></td>
                                                    <td><span class="badge bg-primary">${b.totalFormat}</span></td>
                                                    <td>
                                                        <a data-bs-toggle="tooltip" data-bs-placement="right" class="btn btn-info rounded-pill" title="DETAIL" href="detailBookInStock?id=${b.id}">
                                                            <i class="fa fa-info"></i>
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
                        <div class="col-md-6 col-12">
                            <section class="section">
                                <div class="card">
                                    <div class="card-header">
                                        List All Books
                                    </div>
                                    <div class="card-body">
                                        <table class="table table-hover" id="table2">
                                            <thead>
                                            <tr>
                                                <th>Format Name</th>
                                                <th>Price</th>
                                                <th>Totol Sold</th>
                                                <th>Quantity Stock</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${salesList}" var="s">
                                                <tr>
                                                    <td>
                                                        <span class="badge bg-primary">Hardcover</span>
                                                    </td>
                                                    <td>
                                                        <span class="badge bg-primary">$20</span>
                                                    </td>
                                                    <td>
                                                        <span class="badge bg-primary">25</span>
                                                    </td>
                                                    <td>
                                                        <span class="badge bg-primary">50</span>
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
        // Show success or failed
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
