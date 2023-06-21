<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>STATISTIC REPORT</title>
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
                            <h3>Update Order Detail</h3>
                        </div>
                        <div class="col-12 col-md-6 order-md-2 order-first">
                            <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item"><a href="index.html">Statistic Report</a></li>
                                    <li class="breadcrumb-item active" aria-current="page">Update</li>
                                </ol>
                            </nav>
                        </div>
                    </div>
                </div>
                <section class="section row">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-body">
                                <form:form modelAttribute="oderDetail" action="updateOrdersDetail" method="post" cssClass="form form-horizontal">
                                    <div class="form-body">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <label>Order ID</label>
                                            </div>
                                            <div class="col-md-8 form-group">
                                                <form:input path="id" type="number" cssClass="form-control" readonly="true"/>
                                                <form:input path="orders.id" type="hidden" cssClass="form-control" readonly="true"/>
                                                <form:input path="booksDetails.id" type="hidden" cssClass="form-control" readonly="true"/>
                                                <form:input path="totalPrice" type="hidden" cssClass="form-control" readonly="true"/>
                                            </div>
                                            <div class="col-md-4">
                                                <label>Quantity</label>
                                            </div>
                                            <div class="col-md-8 form-group">
                                                <form:input path="quantity" min="1" type="number" class="form-control" placeholder="Quantity"/>
                                            </div>
                                            <div class="col-sm-12 d-flex justify-content-end">
                                                <button type="submit" class="btn btn-primary me-1 mb-1">Update</button>
                                                <a type="submit"  href="detailOder?id=${oderDetail.orders.id}" class="btn btn-primary me-1 mb-1">Cancel</a>
                                                <button type="reset" class="btn btn-light-secondary me-1 mb-1">Reset</button>
                                            </div>
                                        </div>
                                    </div>
                                </form:form>
                            </div>
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
