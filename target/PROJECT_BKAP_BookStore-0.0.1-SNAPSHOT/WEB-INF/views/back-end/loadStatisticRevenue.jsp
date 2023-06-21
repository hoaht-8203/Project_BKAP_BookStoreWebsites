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
                                            <form:form action="loadOrdersRevenueWithFilter" method="post" cssClass="d-flex">
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
                                <div id="chart-order-revenue"></div>
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

        var optionChartRevenue = {
            annotations: {
                position: 'back'
            },
            dataLabels: {
                enabled:false
            },
            chart: {
                type: 'bar',
                height: 300
            },
            fill: {
                opacity:1
            },
            plotOptions: {
            },
            series: [{
                name: 'sales',
                data: [
                    <c:forEach items="${totalPriceList}" var="p">
                        ${p},
                    </c:forEach>
                ]
            }],
            colors: '#435ebe',
            xaxis: {
                categories: [
                    <c:forEach items="${date}" var="d">
                        "${d.month+1}/${d.date}",
                    </c:forEach>
                ],
            },
        }

        var chartOderRevenue = new ApexCharts(document.querySelector("#chart-order-revenue"), optionChartRevenue);
        chartOderRevenue.render();
    </script>
</body>
</html>
