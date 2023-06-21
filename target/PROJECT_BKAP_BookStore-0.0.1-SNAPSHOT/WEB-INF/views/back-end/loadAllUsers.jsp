<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LIST ALL USER</title>
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
                            <h3>List Users Account</h3>
                        </div>
                        <div class="col-12 col-md-6 order-md-2 order-first">
                            <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item"><a href="admin.jsp">Account Management</a></li>
                                    <li class="breadcrumb-item active" aria-current="page">User Account</li>
                                </ol>
                            </nav>
                        </div>
                    </div>
                </div>
                <section class="section">
                    <div class="card">
                        <div class="card-header">
                            All Information Of Users Account
                        </div>
                        <div class="card-body">
                            <table class="table table-striped" id="table1">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Username</th>
                                    <th>Full Name</th>
                                    <th>Phone</th>
                                    <th>Role</th>
                                    <th>Enabled</th>
                                    <th>Choice</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${listUsers}" var="user">
                                    <tr>
                                        <td>${user.id}</td>
                                        <td>${user.user.userName}</td>
                                        <td>${user.user.fullName}</td>
                                        <td>${user.user.telephone}</td>
                                        <td>${user.role.name}</td>
                                        <td>
                                            <c:if test="${user.user.enabled}">
                                                <span class="badge bg-success">Active</span>
                                            </c:if>
                                            <c:if test="${!user.user.enabled}">
                                                <span class="badge bg-danger">Inactive</span>
                                            </c:if>
                                        </td>
                                        <td>
                                            <a href="detailUser?id=${user.user.id}"><button class="btn btn-info rounded-pill">DETAIL</button></a>
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
        // Simple Datatable
        let table1 = document.querySelector('#table1');
        let dataTable = new simpleDatatables.DataTable(table1);
    </script>
</body>
</html>
