<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ADD AND LIST GENRE</title>
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
                        <h3>Add New Genre</h3>
                    </div>
                    <div class="col-12 col-md-6 order-md-2 order-first">
                        <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                            <ol class="breadcrumb">
                                <li class="breadcrumb-item"><a href="index.html">Book Management</a></li>
                                <li class="breadcrumb-item active" aria-current="page">List Genre</li>
                            </ol>
                        </nav>
                    </div>
                </div>
            </div>

            <!-- Basic Horizontal form layout section start -->
            <section id="basic-horizontal-layouts">
                <div class="row match-height">
                    <div class="col-md-4 col-12">
                        <div class="card">
                            <div class="card-content">
                                <div class="card-body">
                                    <form:form cssClass="form form-horizontal" action="addNewGenre" modelAttribute="genre" method="post">
                                        <div class="form-body">
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <label>Genre name</label>
                                                </div>
                                                <div class="col-md-8 form-group">
                                                    <form:input path="name" cssClass="form-control mb-2" placeholder="Genre Name" type="text"/>
                                                    <form:errors cssClass="alert alert-danger p-1 text-center" path="name"/>
                                                </div>
                                                <div class="col-sm-12 d-flex justify-content-end">
                                                    <button type="submit" class="btn btn-primary me-1 mb-1">Add</button>
                                                    <button type="reset" class="btn btn-light-secondary me-1 mb-1">Reset</button>
                                                </div>
                                            </div>
                                        </div>
                                    </form:form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8 col-12">
                        <section class="section">
                            <div class="card">
                                <div class="card-header">
                                    List All Genre
                                </div>
                                <div class="card-body">
                                    <table class="table table-striped" id="table1">
                                        <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Name</th>
                                            <th>Total Book</th>
                                            <th>Choice</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${genresList}" var="g">
                                                <tr>
                                                    <td>${g.id}</td>
                                                    <td>${g.name}</td>
                                                    <td><span class="badge bg-primary">${g.totalBook}</span></td>
                                                    <td>
                                                        <a data-bs-toggle="tooltip" data-bs-placement="right" class="btn btn-warning rounded-pill w-25" title="UPDATE" href="preUpdateGenre?id=${g.id}">
                                                            <i class="fa fa-hammer"></i>
                                                        </a>
                                                        <a data-bs-toggle="tooltip" data-bs-placement="right" class="btn btn-info rounded-pill w-25" title="DETAIL" href="detailGenre?id=${g.id}">
                                                            <i class="fa fa-info"></i>
                                                        </a>
                                                        <a data-bs-toggle="tooltip" data-bs-placement="right" class="btn btn-danger rounded-pill w-25" title="DETAIL" href="deleteGenre?id=${g.id}"
                                                           onclick="return confirm('Are you sure to delete this genre?')">
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

    var delFailed = "${deleteFailed}";
    if (failed){
        Toastify({
            text: delFailed,
            duration: 3000,
            close:true,
            gravity:"top",
            position: "center",
            backgroundColor: "#dc3545",
        }).showToast();
    }

    var delSuccess = "${deleteSuccess}"
    if (success){
        Toastify({
            text: delSuccess,
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
