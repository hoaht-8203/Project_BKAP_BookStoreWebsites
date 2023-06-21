<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DETAIL AUTHOR</title>
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
                                <li class="breadcrumb-item"><a href="index.html">Book Management</a></li>
                                <li class="breadcrumb-item"><a href="index.html">List Author</a></li>
                                <li class="breadcrumb-item active" aria-current="page">Detail</li>
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
                                    <h4>Detail Author (${author.fullName})</h4>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-lg">
                                            <tr>
                                                <th>ID</th>
                                                <td>${author.id}</td>
                                            </tr>
                                            <tr>
                                                <th>Full Name</th>
                                                <td>${author.fullName}</td>
                                            </tr>
                                            <tr>
                                                <th>Description</th>
                                                <td>${author.description}</td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                    <div class="col-md-6 col-12">
                        <section class="section">
                            <div class="card">
                                <div class="card-header" style="display: flex;">
                                    <h4 class="col-6">Author Name: ${author.fullName}</h4>
                                    <div class="col-6 text-end">
                                        <a class="btn btn-primary" href="loadAllAuthor">Back</a>
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
                                        </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${bookList}" var="b">
                                                <tr>
                                                    <td>${b.id}</td>
                                                    <td>
                                                        <img style="border-radius: 0.5rem; width: 65%;" src="<c:url value="/resources/images/${b.image}"/>" class="img-fluid" alt="This is a book image">
                                                    </td>
                                                    <td>${b.title}</td>
                                                    <td><span class="badge bg-primary">${b.totalSold}</span></td>
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
