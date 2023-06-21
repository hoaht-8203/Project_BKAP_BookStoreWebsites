<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UPDATE SALE</title>
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
                        <h3>Update Sale</h3>
                    </div>
                    <div class="col-12 col-md-6 order-md-2 order-first">
                        <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                            <ol class="breadcrumb">
                                <li class="breadcrumb-item"><a href="index.html">Book Management</a></li>
                                <li class="breadcrumb-item"><a href="index.html">List Sale</a></li>
                                <li class="breadcrumb-item active" aria-current="page">Update Sale</li>
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
                                    <form:form cssClass="form form-horizontal" action="updateSale" modelAttribute="sale" method="post" enctype="multipart/form-data">
                                        <div class="form-body">
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <label>ID</label>
                                                </div>
                                                <div class="col-md-8 form-group">
                                                    <form:input path="id" readonly="true" cssClass="form-control mb-2"/>
                                                    <form:input path="totalBook" readonly="true" cssClass="form-control mb-2" placeholder="Sale Name" type="hidden"/>
                                                    <form:errors cssClass="alert alert-danger p-1 text-center" path="id"/>
                                                </div>
                                                <div class="col-md-4">
                                                    <label>Sale name</label>
                                                </div>
                                                <div class="col-md-8 form-group">
                                                    <form:input path="name" cssClass="form-control mb-2" placeholder="Sale Name" type="text"/>
                                                    <form:errors cssClass="alert alert-danger p-1 text-center" path="name"/>
                                                </div>
                                                <div class="col-md-4">
                                                    <label>Start Date</label>
                                                </div>
                                                <div class="col-md-8 form-group">
                                                    <form:input path="start_date" cssClass="form-control mb-2" type="date"/>
                                                    <form:errors cssClass="alert alert-danger p-1 text-center" path="start_date"/>
                                                </div>
                                                <div class="col-md-4">
                                                    <label>End Date</label>
                                                </div>
                                                <div class="col-md-8 form-group">
                                                    <form:input path="end_date" cssClass="form-control mb-2" type="date"/>
                                                    <form:errors cssClass="alert alert-danger p-1 text-center" path="end_date"/>
                                                    <c:if test="${dateError}">
                                                        <span class="alert alert-danger p-1 text-center">End date is cannot smaller than start date</span>
                                                    </c:if>
                                                </div>
                                                <div class="col-md-4">
                                                    <label>Percentage</label>
                                                </div>
                                                <div class="col-md-8 form-group">
                                                    <form:input path="percentage" cssClass="form-control mb-2" placeholder="Percentage" type="num"/>
                                                    <form:errors cssClass="alert alert-danger p-1 text-center" path="percentage"/>
                                                </div>
                                                <div class="col-md-4">
                                                    <label>Sale Image</label>
                                                </div>
                                                <div class="col-md-8 form-group">
                                                    <input type="file" class="form-control" required="required" name="saleImage"/>
                                                </div>
                                                <div class="col-sm-12 d-flex justify-content-end">
                                                    <button type="submit" class="btn btn-primary me-1 mb-1">Update</button>
                                                    <button type="reset" class="btn btn-light-secondary me-1 mb-1">Reset</button>
                                                </div>
                                            </div>
                                        </div>
                                    </form:form>
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
