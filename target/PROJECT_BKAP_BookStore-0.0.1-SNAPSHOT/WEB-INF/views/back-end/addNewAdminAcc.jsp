<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
                            <h3>Add New Admin Account</h3>
                        </div>
                        <div class="col-12 col-md-6 order-md-2 order-first">
                            <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item"><a href="admin.jsp">Account Management</a></li>
                                    <li class="breadcrumb-item active" aria-current="page">Add Admin Account</li>
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
                                    <h4 class="card-title">Please enter the information</h4>
                                </div>
                                <div class="card-content">
                                    <div class="card-body">
                                        <form:form action="addNewAdminAcc" method="post" modelAttribute="user" cssClass="form form-horizontal">
                                            <div class="form-body">
                                                <div class="row">
                                                    <div class="col-md-4">
                                                        <label>Full Name</label>
                                                    </div>
                                                    <div class="col-md-8 form-group">
                                                        <form:input path="fullName" type="text" cssClass="form-control" placeholder="Full Name"/>
                                                        <form:errors cssClass="alert alert-danger p-1 text-center" path="fullName"/>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label>Gender</label>
                                                    </div>
                                                    <div class="col-md-8 form-group">
                                                        <form:radiobutton path="gender" value="true" cssClass="form-check-input"/> Male
                                                        <form:radiobutton path="gender" value="false" cssClass="form-check-input"/> Female
                                                        <br>
                                                        <form:errors cssClass="alert alert-danger p-1 text-center" path="gender"/>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label>Birthday</label>
                                                    </div>
                                                    <div class="col-md-8 form-group">
                                                        <form:input path="birthday" type="date" cssClass="form-control"/>
                                                        <form:errors cssClass="alert alert-danger p-1 text-center" path="birthday"/>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label>Address</label>
                                                    </div>
                                                    <div class="col-md-8 form-group">
                                                        <form:input path="address" type="text" cssClass="form-control" placeholder="Address"/>
                                                        <form:errors cssClass="alert alert-danger p-1 text-center" path="address"/>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label>Email</label>
                                                    </div>
                                                    <div class="col-md-8 form-group">
                                                        <form:input path="email" type="email" cssClass="form-control" placeholder="Email"/>
                                                        <form:errors cssClass="alert alert-danger p-1 text-center" path="email"/>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label>Telephone</label>
                                                    </div>
                                                    <div class="col-md-8 form-group">
                                                        <form:input path="telephone" type="tel" cssClass="form-control" placeholder="Telephone"/>
                                                        <form:errors cssClass="alert alert-danger p-1 text-center" path="telephone"/>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label>Username</label>
                                                    </div>
                                                    <div class="col-md-8 form-group">
                                                        <form:input path="userName" type="text" cssClass="form-control" placeholder="Username"/>
                                                        <span style="color: red">${failedUserName}</span>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label>Password</label>
                                                    </div>
                                                    <div class="col-md-8 form-group">
                                                        <form:input path="passWord" type="password" cssClass="form-control" placeholder="Password"/>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label>Confirm password</label>
                                                    </div>
                                                    <div class="col-md-8 form-group">
                                                        <input name="confirmPassword" type="password" class="form-control" placeholder="Confirm Password">
                                                    </div>
                                                    <div class="col-sm-12 d-flex justify-content-end">
                                                        <span style="color: red">${failed1}</span>
                                                        <div>
                                                            <button type="submit" class="btn btn-primary me-1 mb-1">Add</button>
                                                            <button type="reset" class="btn btn-light-secondary me-1 mb-1">Reset</button>
                                                        </div>
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
