<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LIST ALL BOOK</title>
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
                            <h3>List All Books</h3>
                        </div>
                        <div class="col-12 col-md-6 order-md-2 order-first">
                            <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item"><a href="loadAllBooks">Book Management</a></li>
                                    <li class="breadcrumb-item active" aria-current="page">List All Books</li>
                                </ol>
                            </nav>
                        </div>
                    </div>
                </div>
                <section class="section">
                    <div class="card">
                        <div class="card-header">
                            <a href="initAddNewBook" style="display: flex; justify-content: start;"><button class="btn btn-primary rounded-pill">ADD NEW BOOK</button></a>
                        </div>
                        <div class="card-body">
                            <table class="table table-hover" id="table1">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th class="col-2">Image</th>
                                        <th class="col-6">Title</th>
                                        <th class="col-1">Create Date</th>
                                        <th>Sold</th>
                                        <th>Total Format</th>
                                        <th class="col-1">Choice</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listBooks}" var="b">
                                        <tr>
                                            <td>${b.id}</td>
                                            <td>
                                                <img style="border-radius: 0.5rem; width: 65%;" src="<c:url value="/resources/images/${b.image}"/>" class="img-fluid" alt="This is a book image">
                                            </td>
                                            <td>${b.title}</td>
                                            <td><fmt:formatDate value="${b.create_date}" type="date" pattern="yyyy-MM-dd"/></td>
                                            <td><span class="badge bg-primary">${b.totalSold}</span></td>
                                            <td class="text-center">
                                                <c:if test="${b.totalFormat == 0}">
                                                    <span class="badge bg-danger">${b.totalFormat}</span>
                                                </c:if>
                                                <c:if test="${b.totalFormat > 0}">
                                                    <span class="badge bg-success">${b.totalFormat}</span>
                                                </c:if>
                                            </td>
                                            <td>
                                                <a data-bs-toggle="tooltip" data-bs-placement="right" class="btn btn-info rounded-pill mb-2 w-75" title="DETAIL" href="detailBook?id=${b.id}">
                                                    <i class="fa fa-info"></i>
                                                </a> <br>
                                                <a data-bs-toggle="tooltip" data-bs-placement="right" class="btn btn-warning rounded-pill mb-2 w-75" title="UPDATE" href="preUpdateBook?id=${b.id}">
                                                    <i class="fa fa-hammer"></i>
                                                </a> <br>
                                                <a data-bs-toggle="tooltip" data-bs-placement="right" class="btn btn-danger rounded-pill w-75" title="DELETE" href="deleteBook?id=${b.id}"
                                                    onclick="return confirm('THIS ACTION CAN DELETE ALL FORMAT OF THIS BOOK \nAND CANNOT RETURN \nARE YOU SURE TO DELETE?')">
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
