<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ADD NEW ADMIN ACCOUNT</title>
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
                            <h3>My Account</h3>
                        </div>
                        <div class="col-12 col-md-6 order-md-2 order-first">
                            <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                                <ol class="breadcrumb">
                                    <!-- <li class="breadcrumb-item"><a href="admin.jsp">Account Management</a></li> -->
                                    <li class="breadcrumb-item active" aria-current="page">My Account</li>
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
                                    <h4 class="card-title">The Information</h4>
                                    <div>
                                        <a href="preUpdateMyAccount" class="btn btn-primary">Update</a>
                                    </div>
                                </div>
                                <div class="card-content">
                                    <div class="card-body">
                                        <table class="table table-lg">
                                            <tr>
                                                <th>Full Name</th>
                                                <td>${user.fullName}</td>
                                            </tr>
                                            <tr>
                                                <th>Gender</th>
                                                <td>${user.gender?"Male":"Female"}</td>
                                            </tr>
                                            <tr>
                                                <th>Birthday</th>
                                                <td>
                                                    <fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>Address</th>
                                                <td>${user.address}</td>
                                            </tr>
                                            <tr>
                                                <th>Email</th>
                                                <td>${user.email}</td>
                                            </tr>
                                            <tr>
                                                <th>Telephone</th>
                                                <td>${user.telephone}</td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 col-md-6">
                            <div class="card">
                                <div class="card-header d-flex justify-content-between">
                                    <h4 class="card-title">Change Password</h4>
                                </div>
                                <div class="card-content">
                                    <div class="card-body">
                                        <form:form action="updatePasswordMyAccount" method="post" cssClass="form form-horizontal">
                                            <div class="form-body">
                                                <div class="row">
                                                    <div class="col-md-4">
                                                        <label>Old Password</label>
                                                    </div>
                                                    <div class="col-md-8 form-group">
                                                        <input type="password" name="oldPassword" class="form-control" placeholder="Old Password">
                                                        <span style="color: red">${failedOldPassword}</span>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label>New Password</label>
                                                    </div>
                                                    <div class="col-md-8 form-group">
                                                        <input type="password" name="newPassword" class="form-control" placeholder="New Password">
                                                        <span style="color: red">${failedNewPassword}</span>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label>Confirm Password</label>
                                                    </div>
                                                    <div class="col-md-8 form-group">
                                                        <input type="password" name="confirmPassword" class="form-control"placeholder="Confirm Password">
                                                        <span style="color: red">${failedConfirmPassword}</span>
                                                    </div>
                                                    <div class="col-sm-12 d-flex justify-content-end">
                                                        <button type="submit" class="btn btn-primary me-1 mb-1">Change</button>
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
