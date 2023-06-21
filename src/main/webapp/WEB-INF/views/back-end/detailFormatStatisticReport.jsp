<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                            <h3>${books.title}</h3>
                        </div>
                        <div class="col-12 col-md-6 order-md-2 order-first">
                            <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item"><a href="loadAllBooks">Statistic Report</a></li>
                                    <li class="breadcrumb-item active" aria-current="page">Detail</li>
                                </ol>
                            </nav>
                        </div>
                    </div>
                </div>
                <!-- Basic card section start -->
                <section id="content-types">
                    <div class="row">
                        <div class="col-xl-2 col-md-6">
                            <div class="card">
                                <div class="card-content">
                                    <img src="<c:url value="/resources/images/${books.image}"/> " class="img-fluid" alt="${books.image}" style="border-radius: 0.5rem;">
                                </div>
                            </div>
                        </div>

                        <div class="col-xl-4 col-md-5 col-sm-5">
                            <div class="card">
                                <div class="card-content">
                                    <div class="card-body row">
                                        <div class="col-12 col-md-12">
                                            <h4 class="text-decoration-underline">Sold Of Each Format</h4>
                                            <table class="table table-hover" id="table">
                                                <thead>
                                                <tr>
                                                    <th>Format Name</th>
                                                    <th>Publisher</th>
                                                    <th>Price</th>
                                                    <th>Total Sold</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${listFormat}" var="f">
                                                    <tr>
                                                        <td><a href="getDetailFormatStatistic?id=${f.id}" class="btn btn-primary">${f.formats.name}</a></td>
                                                        <td>${f.publishers}</td>
                                                        <td>
                                                            <span class="badge bg-primary">
                                                                <fmt:formatNumber value="${f.price}" type="currency"/>
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <span class="badge bg-primary">${f.quantitySold}</span>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-xl-6 col-md-5 col-sm-5">
                            <div class="card">
                                <div class="card-content">
                                    <div class="card-body row">
                                        <div class="col-12 col-md-12">
                                            <h4 class="text-decoration-underline">Detail Sold Of ${formatName}</h4>
                                            <table class="table table-hover" id="table1">
                                                <thead>
                                                <tr>
                                                    <th>Date Sold</th>
                                                    <th>Quantity Sold</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${soldList}" var="s">
                                                    <tr>
                                                        <td>
                                                            <fmt:formatDate value="${s.date_sold}" pattern="yyyy-MM-dd"/>
                                                        </td>
                                                        <td>
                                                            <span class="badge bg-success">${s.quantitySold}</span>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-xl-12 col-md-5 col-sm-5">
                            <div class="card">
                                <div class="card-header">
                                    <h4>Statistics Table</h4>
                                </div>
                                <div class="card-body">
                                    <div id="chart-quatity-sold"></div>
                                </div>
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

        var optionsProfileVisit = {
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
                name: 'Sold',
                data: [<c:forEach items="${soldList}" var="s">
                            ${s.quantitySold},
                        </c:forEach>]
            }],
            colors: '#435ebe',
            xaxis: {
                categories: [<c:forEach items="${soldList}" var="s">
                                "${s.date_sold}",
                            </c:forEach>],
            },
        }
        var chartProfileVisit = new ApexCharts(document.querySelector("#chart-quatity-sold"), optionsProfileVisit);
        chartProfileVisit.render();
    </script>
</body>
</html>
