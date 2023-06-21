<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DETAIL USER</title>
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
                            <h3>Detail of ${userById.userName}</h3>
                        </div>
                        <div class="col-12 col-md-6 order-md-2 order-first">
                            <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item"><a href="admin.jsp">Account Management</a></li>
                                    <li class="breadcrumb-item active" aria-current="page">Users Detail</li>
                                </ol>
                            </nav>
                        </div>
                    </div>
                </div>
                <section class="section">
                    <div class="row" id="basic-table">
                        <div class="col-12 col-md-6">
                            <div class="card">
                                <div class="card-header d-flex justify-content-between">
                                    <h4 class="card-title">Detail Information</h4>
                                    <div>
                                        <c:choose>
                                            <c:when test="${userById.enabled}">
                                                <a href="banAccUser?id=${userById.id}" onclick="return confirm('Are you sure to ban this account?')" class="btn btn-warning">Ban</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="unBanAccUser?id=${userById.id}" onclick="return confirm('Are you sure to unban this account?')" class="btn btn-warning">Unban</a>
                                            </c:otherwise>
                                        </c:choose>
                                        <a href="deleteAccUser?id=${userById.id}" onclick="return confirm('Are you sure to delete this account?')" class="btn btn-danger">Delete</a>
                                        <a href="loadAllAcc" class="btn btn-primary">Back</a>
                                    </div>
                                </div>
                                <div class="card-content">
                                    <div class="card-body">
                                        <!-- Table with outer spacing -->
                                        <div class="table-responsive">
                                            <table class="table table-lg">
                                                <tr>
                                                    <th>ID</th>
                                                    <td>
                                                        ${userById.id}
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>Username</th>
                                                    <td>
                                                        ${userById.userName}
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>Enabled</th>
                                                    <td>
                                                        <c:if test="${userById.enabled}">
                                                            <span class="badge bg-success">Active</span>
                                                        </c:if>
                                                        <c:if test="${!userById.enabled}">
                                                            <span class="badge bg-danger">Inactive</span>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>Full Name</th>
                                                    <td>
                                                        ${userById.fullName}
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>Gender</th>
                                                    <td>
                                                        ${userById.gender?"Male":"Female"}
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>Birthday</th>
                                                    <td>
                                                        <fmt:formatDate value="${userById.birthday}" type="date" pattern="yyyy-MM-dd"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>Address</th>
                                                    <td>
                                                        ${userById.address}
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>Email</th>
                                                    <td>
                                                        ${userById.email}
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>Telephone</th>
                                                    <td>
                                                        ${userById.telephone}
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
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
    </script>
</body>
</html>
