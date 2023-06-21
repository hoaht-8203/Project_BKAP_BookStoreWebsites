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
                                <form:form modelAttribute="order" action="updateOrders" method="post" cssClass="form form-horizontal">
                                    <div class="form-body">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <label>Order ID</label>
                                            </div>
                                            <div class="col-md-8 form-group">
                                                <form:input path="id" type="number" cssClass="form-control" readonly="true"/>
                                                <form:input path="status" type="hidden" cssClass="form-control" readonly="true"/>
                                                <form:input path="totalPriceOrder" type="hidden" cssClass="form-control" readonly="true"/>
                                            </div>
                                            <div class="col-md-4">
                                                <label>Full Name</label>
                                            </div>
                                            <div class="col-md-8 form-group">
                                                <form:input path="fullName" type="text" id="email-id" class="form-control" placeholder="Full Name"/>
                                                <form:errors cssClass="alert alert-danger p-1 text-center" path="fullName"/>
                                            </div>
                                            <div class="col-md-4">
                                                <label>Email</label>
                                            </div>
                                            <div class="col-md-8 form-group">
                                                <form:input path="email" type="email" class="form-control" placeholder="Email"/>
                                                <form:errors cssClass="alert alert-danger p-1 text-center" path="email"/>
                                            </div>
                                            <div class="col-md-4">
                                                <label>Telephone</label>
                                            </div>
                                            <div class="col-md-8 form-group">
                                                <form:input path="phoneNumber" type="tel" class="form-control" placeholder="Telephone"/>
                                                <form:errors cssClass="alert alert-danger p-1 text-center" path="phoneNumber"/>
                                            </div>
                                            <div class="col-md-4">
                                                <label>Address</label>
                                            </div>
                                            <div class="col-md-8 form-group">
                                                <form:input path="address" type="text" class="form-control" placeholder="Address"/>
                                                <form:errors cssClass="alert alert-danger p-1 text-center" path="address"/>
                                            </div>
                                            <div class="col-md-4">
                                                <label>Order Date</label>
                                            </div>
                                            <div class="col-md-8 form-group">
                                                <form:input path="orderDate" type="text" class="form-control" readonly="true"/>
                                            </div>
                                            <div class="col-md-4">
                                                <label>Type Ship</label>
                                            </div>
                                            <div class="col-md-8 form-group">
                                                <form:input path="typeShip" type="text" class="form-control" readonly="true"/>
                                            </div>
                                            <div class="col-sm-12 d-flex justify-content-end">
                                                <button type="submit" class="btn btn-primary me-1 mb-1">Update</button>
                                                <a type="submit"  href="detailOder?id=${order.id}" class="btn btn-primary me-1 mb-1">Cancel</a>
                                                <button type="reset" class="btn btn-light-secondary me-1 mb-1">Reset</button>
                                            </div>
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                        </div>
                    </div>
                    <div class="col-4">
                        <div class="card">
                            <div class="card-body">
                                <c:forEach items="${orderDetailList}" var="od">
                                    <table class="table table-bordered table-hover row">
                                        <tr>
                                            <th class="col-3">Book ID</th>
                                            <td class="">${od.booksDetails.id}</td>
                                        </tr>
                                        <tr>
                                            <th>Book Name</th>
                                            <td>${od.booksDetails.books.title}</td>
                                        </tr>
                                        <tr>
                                            <th>Format</th>
                                            <td><span class="badge bg-primary">${od.booksDetails.formats.name}</span></td>
                                        </tr>
                                        <tr>
                                            <th>Quantity</th>
                                            <td>
                                                ${od.quantity}
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Book Price</th>
                                            <td>
                                            <span class="badge bg-primary">
                                                <fmt:formatNumber value="${od.booksDetails.priceAfterSale}" type="currency"/>
                                            </span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Total Price</th>
                                            <td>
                                            <span class="badge bg-primary">
                                                <fmt:formatNumber value="${od.totalPrice}" type="currency"/>
                                            </span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Status</th>
                                            <td>
                                                <c:if test="${od.booksDetails.available}">
                                                    <span class="badge bg-success">Available</span>
                                                </c:if>
                                                <c:if test="${!od.booksDetails.available}">
                                                    <span class="badge bg-danger">Pre Oder</span>
                                                </c:if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Choice</th>
                                            <td>
                                                <a href="preUpdateOrderDetail?id=${od.id}" class="btn btn-primary rounded-pill">Update</a>
                                                <a href="removeOderDetail?id=${od.id}" onclick="return confirm('Are you sure to remove this order')" class="btn btn-danger rounded-pill">Remove</a>
                                            </td>
                                        </tr>
                                    </table>
                                </c:forEach>
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
