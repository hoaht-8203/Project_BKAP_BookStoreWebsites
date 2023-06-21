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
                        <h3>Statistic Report</h3>
                    </div>
                    <div class="col-12 col-md-6 order-md-2 order-first">
                        <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                            <ol class="breadcrumb">
                                <li class="breadcrumb-item active" aria-current="page">Statistic Report</li>
                            </ol>
                        </nav>
                    </div>
                </div>
            </div>
            <section class="section row">
                <div class="col-8">
                    <div class="card">
                        <div class="card-header">
                            <div class="form-body">
                                <div class="row">
                                    <div class="col-8">
                                        <form:form action="loadOrdersWithFilter" method="post" cssClass="d-flex">
                                            <div class="col-md-2 form-group" style="margin-right: 10px">
                                                Start Date<input type="date" value="${startDate}" name="startDate" class="form-control">
                                            </div>
                                            <div class="col-md-2 form-group" style="margin-right: 10px">
                                                End Date<input type="date" value="${endDate}" name="endDate" class="form-control">
                                            </div>
                                            <div class="col-md-2 form-group" style="margin-right: 10px">
                                                Status
                                                <select class="form-select" name="status">
                                                    <option value="-3">All</option>
                                                    <option value="0">Unprocessed</option>
                                                    <option value="-1">No Processing</option>
                                                    <option value="2">Delivery</option>
                                                    <option value="1">Delivery Successful</option>
                                                    <option value="-2">Delivery Failed</option>
                                                </select>
                                            </div>
                                            <div class="col-md-1 form-group d-flex align-items-end">
                                                <button type="submit" class="btn btn-primary me-1 mb-1">Apply</button>
                                            </div>
                                        </form:form>
                                    </div>
                                    <div class="col-4 d-flex align-items-center justify-content-end">
                                        <div class="form-group">
                                            <a href="loadOrdersCurrentDate"><button type="submit" class="btn btn-primary me-1 mb-1">Load Current Date</button></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <table class="table table-hover" id="table1">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Full Name</th>
                                    <th>Email</th>
                                    <th>Telephone</th>
                                    <th>Order Date</th>
                                    <th>Status</th>
                                    <th>Choice</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${ordersList}" var="o">
                                        <tr>
                                            <td>${o.id}</td>
                                            <td>${o.fullName}</td>
                                            <td>${o.email}</td>
                                            <td>${o.phoneNumber}</td>
                                            <td>
                                                <fmt:formatDate value="${o.orderDate}" pattern="yyyy-MM-dd"/>
                                            </td>
                                            <td class="">
                                                <c:if test="${o.status == 0}">
                                                    <span class="badge bg-warning">Unprocessed</span>
                                                </c:if>
                                                <c:if test="${o.status == -1}">
                                                    <span class="badge bg-danger">No Processing</span>
                                                </c:if>
                                                <c:if test="${o.status == 2}">
                                                    <span class="badge bg-primary">Delivery</span>
                                                </c:if>
                                                <c:if test="${o.status == 1}">
                                                    <span class="badge bg-success">Delivery Successful</span>
                                                </c:if>
                                                <c:if test="${o.status == -2}">
                                                    <span class="badge bg-secondary">Delivery Failed</span>
                                                </c:if>
                                                <c:if test="${o.status == 3}">
                                                    <span class="badge" style="background-color: #ff8000">Pre-orders</span>
                                                </c:if>
                                            </td>
                                            <td>
                                                <a data-bs-toggle="tooltip" data-bs-placement="right" class="btn btn-info rounded-pill mb-2 w-75" title="DETAIL" href="detailOder?id=${o.id}">
                                                    <i class="fa fa-info"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="col-4">
                    <div class="card">
                        <div class="card-header d-flex justify-content-between">
                            <div>
                                <c:if test="${orders.status == 0}">
                                    <a href="updateStatusOrders?orderId=${orders.id}&statusId=2" onclick="return confirm('Are you sure to apply this order?')"><button class="btn btn-primary me-1 mb-1 mb-3">Apply</button></a>
                                </c:if>
                                <a href="updateStatusOrders?orderId=${orders.id}&statusId=-1" onclick="return confirm('Are you sure to remove this order?')"><button class="btn btn-danger me-1 mb-1 mb-3">Remove</button></a>
                                <a href="preUpdateOrders?id=${orders.id}"><button class="btn btn-warning me-1 mb-1 mb-3">Update</button></a>
                            </div>
                            <div>
                                <h4><span class="badge bg-primary">Total: <fmt:formatNumber value="${totalPrice}" type="currency"/></span></h4>
                            </div>
                        </div>
                        <div class="card-body">
                            <h5 class="ml-2 mb-2">1. Information:</h5>

                            <table class="table table-bordered mb-0">
                                <tr>
                                    <th>Order ID</th>
                                    <td>${orders.id}</td>
                                </tr>
                                <tr>
                                    <th>Full Name</th>
                                    <td>${orders.fullName}</td>
                                </tr>
                                <tr>
                                    <th>Email</th>
                                    <td>${orders.email}</td>
                                </tr>
                                <tr>
                                    <th>Telephone</th>
                                    <td>${orders.phoneNumber}</td>
                                </tr>
                                <tr>
                                    <th>Address</th>
                                    <td>${orders.address}</td>
                                </tr>
                                <tr>
                                    <th>Order Date</th>
                                    <td><span class="badge bg-primary">${orders.orderDate}</span></td>
                                </tr>
                                <tr>
                                    <th>Type Ship</th>
                                    <td><span class="badge bg-primary">${orders.typeShip}</span></td>
                                </tr>
                                <tr>
                                    <th>Status</th>
                                    <td>
                                        <c:if test="${orders.status == 0}">
                                            <span class="badge bg-warning">Unprocessed</span>
                                        </c:if>
                                        <c:if test="${orders.status == -1}">
                                            <span class="badge bg-danger">No Processing</span>
                                        </c:if>
                                        <c:if test="${orders.status == 2}">
                                            <span class="badge bg-primary">Delivery</span>
                                        </c:if>
                                        <c:if test="${orders.status == 1}">
                                            <span class="badge bg-success">Delivery Successful</span>
                                        </c:if>
                                        <c:if test="${orders.status == -2}">
                                            <span class="badge bg-secondary">Delivery Failed</span>
                                        </c:if>
                                        <c:if test="${orders.status == 3}">
                                            <span class="badge" style="background-color: #ff8000">Pre-orders</span>
                                        </c:if>
                                    </td>
                                </tr>
                            </table>

                            <h5 class="m-2">2. Product List:</h5>

                            <c:forEach items="${ordersDetail}" var="od">
                                <table class="table table-bordered table-hover mt-3 row">
                                    <tr>
                                        <th class="col-3">Book ID</th>
                                        <td>${od.booksDetails.books.id}</td>
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
                                        <td>${od.quantity}</td>
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
                                </table>
                            </c:forEach>
                        </div>
                        <div class="card-body">
                            <c:if test="${orders.status == 2}">
                                <a href="updateStatusOrders?orderId=${orders.id}&statusId=1" onclick="return confirm('Are you sure to completed delivery this order?')"><button class="btn btn-success me-1 mb-1 mb-3">Delivery Completed</button></a>
                                <a href="updateStatusOrders?orderId=${orders.id}&statusId=-2" onclick="return confirm('Are you sure not completed delivery this order?')"><button class="btn btn-secondary me-1 mb-1 mb-3">Delivery Incompleted</button></a>
                            </c:if>
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
