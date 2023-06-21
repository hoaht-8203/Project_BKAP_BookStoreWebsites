<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LIST ALL AUTHOR</title>
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
                        <h3>LIST ALL AUTHOR</h3>
                    </div>
                    <div class="col-12 col-md-6 order-md-2 order-first">
                        <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                            <ol class="breadcrumb">
                                <li class="breadcrumb-item"><a href="loadAllBooks">Book Management</a></li>
                                <li class="breadcrumb-item active" aria-current="page">List All Author</li>
                            </ol>
                        </nav>
                    </div>
                </div>
            </div>

            <!-- Basic Horizontal form layout section start -->
            <section class="section">
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-body">
                                <ul class="nav nav-tabs mb-4" id="myTab" role="tablist">
                                    <li class="nav-item" role="presentation">
                                        <a class="nav-link ${listAuthorActive}" id="home-tab" data-bs-toggle="tab" href="#home"
                                           role="tab" aria-controls="home" aria-selected="true">List Author</a>
                                    </li>
                                    <li class="nav-item" role="presentation">
                                        <a class="nav-link ${addNewAuthorActive}" id="profile-tab" data-bs-toggle="tab" href="#profile"
                                           role="tab" aria-controls="profile" aria-selected="false">Add New Author</a>
                                    </li>
                                </ul>
                                <div class="tab-content" id="myTabContent">
                                    <div class="tab-pane fade show ${listAuthorActive}" id="home" role="tabpanel" aria-labelledby="home-tab">
                                        <table class="table table-striped" id="table1">
                                            <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Author Name</th>
                                                <th class="w-75">Author Description</th>
                                                <th>Total Book</th>
                                                <th>Choice</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${authorList}" var="a">
                                                    <tr>
                                                        <td>${a.id}</td>
                                                        <td>${a.fullName}</td>
                                                        <td>${a.description}</td>
                                                        <td><span class="badge bg-primary">${a.totalBook}</span></td>
                                                        <td>
                                                            <a data-bs-toggle="tooltip" data-bs-placement="right" class="btn btn-warning rounded-pill mb-2 w-75" title="UPDATE" href="preUpdateAuthor?id=${a.id}">
                                                                <i class="fa fa-hammer"></i>
                                                            </a> <br>
                                                            <a data-bs-toggle="tooltip" data-bs-placement="right" class="btn btn-info rounded-pill mb-2 w-75" title="DETAIL" href="detailAuthor?id=${a.id}">
                                                                <i class="fa fa-info"></i>
                                                            </a> <br>
                                                            <a data-bs-toggle="tooltip" data-bs-placement="right" class="btn btn-danger rounded-pill w-75" title="DELETE" href="deleteAuthor?id=${a.id}"
                                                               onclick="return confirm('THIS ACTION CAN DELETE THE AUTHOR OF BOOK THAT YOU CREATED \n AND CANNOT RETURN \n ARE YOU SURE TO DELETE?')">
                                                                <i class="fa fa-trash"></i>
                                                            </a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="tab-pane fade show ${addNewAuthorActive}" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                                        <div class="col-9">
                                            <spring:form cssClass="form form-horizontal" action="addNewAuthor" method="post" modelAttribute="author">
                                                <div class="form-body">
                                                    <div class="row">
                                                        <div class="col-md-2">
                                                            <label>Author Name</label>
                                                        </div>
                                                        <div class="col-md-5 form-group">
                                                            <spring:input path="fullName" cssClass="form-control mb-2" placeholder="Author Name"/>
                                                            <spring:errors path="fullName" cssClass="alert alert-danger p-1 text-center"/>
                                                        </div>
                                                        <div class="col-md-5">

                                                        </div>
                                                        <div class="col-md-2">
                                                            <label>About The Author</label>
                                                        </div>
                                                        <div class="col-md-10 form-group">
                                                            <spring:textarea path="description" cssClass="form-control mb-2" cols="85" rows="7"/>
                                                            <spring:errors path="description" cssClass="alert alert-danger p-1 text-center"/>
                                                        </div>
                                                        <div class="col-sm-12 d-flex justify-content-end">
                                                            <button type="submit" class="btn btn-primary me-1 mb-1">Submit</button>
                                                            <button type="reset" class="btn btn-light-secondary me-1 mb-1">Reset</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </spring:form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- // Basic Horizontal form layout section end -->
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
