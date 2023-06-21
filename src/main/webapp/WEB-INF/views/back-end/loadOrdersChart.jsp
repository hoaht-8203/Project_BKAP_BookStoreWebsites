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
                            <h3>Order Chart</h3>
                        </div>
                        <div class="col-12 col-md-6 order-md-2 order-first">
                            <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item active" aria-current="page">Order Chart</li>
                                </ol>
                            </nav>
                        </div>
                    </div>
                </div>
                <section class="section row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header">
                                <div class="form-body">
                                    <div class="row">
                                        <div class="col-8">
                                            <form:form action="loadOrdersChartWithFilter" method="post" cssClass="d-flex">
                                                <div class="col-md-3 form-group" style="margin-right: 10px">
                                                    Number of days to date
                                                    <select class="form-select" name="numberOfDate">
                                                        <option value="5">5</option>
                                                        <option value="10">10</option>
                                                        <option value="15">15</option>
                                                        <option value="20">20</option>
                                                        <option value="25">25</option>
                                                        <option value="30">30</option>
                                                    </select>
                                                </div>
                                                <div style="padding-top: 23px" class="col-md-1 form-group d-flex align-items-end">
                                                    <button type="submit" class="btn btn-primary me-1 mb-1">Apply</button>
                                                </div>
                                            </form:form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body">
                                <div id="order-chart-report"></div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </div>

    <jsp:include page="import_js.jsp"/>
    <script>
        <%--var failed = "${failed}";--%>
        <%--if (failed){--%>
        <%--    Toastify({--%>
        <%--        text: failed,--%>
        <%--        duration: 3000,--%>
        <%--        close:true,--%>
        <%--        gravity:"top",--%>
        <%--        position: "center",--%>
        <%--        backgroundColor: "#dc3545",--%>
        <%--    }).showToast();--%>
        <%--}--%>

        <%--var success = "${success}"--%>
        <%--if (success){--%>
        <%--    Toastify({--%>
        <%--        text: success,--%>
        <%--        duration: 3000,--%>
        <%--        close:true,--%>
        <%--        gravity:"top",--%>
        <%--        position: "center",--%>
        <%--        backgroundColor: "#4caf50",--%>
        <%--    }).showToast();--%>
        <%--}--%>
        <%--// Simple Datatable--%>
        <%--let table1 = document.querySelector('#table1');--%>
        <%--let dataTable = new simpleDatatables.DataTable(table1);--%>

        <%--var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))--%>
        <%--var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {--%>
        <%--    return new bootstrap.Tooltip(tooltipTriggerEl)--%>
        <%--})--%>

        var barOderOptions = {
            series: [
                {
                    name: "Unprocessed",
                    data: [
                        <c:forEach items="${unProcessedList}" var="n">
                            ${n},
                        </c:forEach>
                    ],
                    color: "#ffc107"
                },
                {
                    name: "Delivery",
                    data: [
                        <c:forEach items="${deliveryList}" var="n">
                            ${n},
                        </c:forEach>
                    ],
                    color: "#435ebe",
                },
                {
                    name: "Delivery Successful",
                    data: [
                        <c:forEach items="${deliverySuccessList}" var="n">
                            ${n},
                        </c:forEach>
                    ],
                    color: "#198754",
                },
                {
                    name: "No Processing",
                    data: [
                        <c:forEach items="${noProcessingList}" var="n">
                            ${n},
                        </c:forEach>
                    ],
                    color: "#dc3545",
                },
                {
                    name: "Delivery Failed",
                    data: [
                        <c:forEach items="${deliveryFailedList}" var="n">
                            ${n},
                        </c:forEach>
                    ],
                    color: "#6c757d",
                },
                {
                    name: "Pre-Orders",
                    data: [
                        <c:forEach items="${preOrdersList}" var="n">
                        ${n},
                        </c:forEach>
                    ],
                    color: "#ff8000",
                },
            ],
            chart: {
                type: "bar",
                height: 350,
            },
            plotOptions: {
                bar: {
                    horizontal: false,
                    columnWidth: "55%",
                    endingShape: "rounded",
                },
            },
            dataLabels: {
                enabled: false,
            },
            stroke: {
                show: true,
                width: 2,
                colors: ["transparent"],
            },
            xaxis: {
                categories: [
                    <c:forEach items="${date}" var="d">
                        "${d.month+1}/${d.date}",
                    </c:forEach>
                ],
            },
            yaxis: {
                title: {
                    text: "(quantity)",
                },
            },
            fill: {
                opacity: 1,
            },
            tooltip: {
                y: {
                    formatter: function(val) {
                        return "Quantity: " + val;
                    },
                },
            },
        };
        var barOrder = new ApexCharts(document.querySelector("#order-chart-report"), barOderOptions);
        barOrder.render();
    </script>
</body>
</html>
