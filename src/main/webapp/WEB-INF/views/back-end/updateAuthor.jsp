<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UPDATE AUTHOR</title>
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
                            <h3>Update Author</h3>
                        </div>
                        <div class="col-12 col-md-6 order-md-2 order-first">
                            <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item"><a href="loadAllBooks">Book Management</a></li>
                                    <li class="breadcrumb-item">List All Author</li>
                                    <li class="breadcrumb-item active" aria-current="page">Update Author</li>
                                </ol>
                            </nav>
                        </div>
                    </div>
                </div>

                <!-- Basic Horizontal form layout section start -->
                <section id="basic-horizontal-layouts">
                    <div class="row match-height">
                        <div class="col-md-9 col-12">
                            <div class="card">
                                <div class="card-content">
                                    <div class="card-body">
                                        <spring:form cssClass="form form-horizontal" action="updateAuthor" method="post" modelAttribute="author">
                                            <div class="form-body">
                                                <div class="row">
                                                    <div class="col-md-2">
                                                        <label>Author ID</label>
                                                    </div>
                                                    <div class="col-md-5 form-group">
                                                        <spring:input path="id" cssClass="form-control" readonly="true"/>
                                                    </div>
                                                    <div class="col-md-5">

                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Author Name</label>
                                                    </div>
                                                    <div class="col-md-5 form-group">
                                                        <spring:input path="fullName" cssClass="form-control mb-2" placeholder="Author Name"/>
                                                        <spring:errors path="fullName" cssClass="alert alert-danger p-1 text-center"/>
                                                    </div>
                                                    <div class="col-md-5">

                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>About The Author</label>
                                                    </div>
                                                    <div class="col-md-10 form-group">
                                                        <spring:textarea path="description" cssClass="form-control mb-2" cols="85" rows="7"/>
                                                        <spring:errors path="description" cssClass="alert alert-danger p-1 text-center"/>
                                                    </div>
                                                    <div class="col-sm-12 d-flex justify-content-end">
                                                        <button type="submit" class="btn btn-primary me-1 mb-1">Update</button>
                                                        <a href="loadAllAuthor" class="btn btn-light-secondary me-1 mb-1">Cancel</a>
                                                        <button type="reset" class="btn btn-light-secondary me-1 mb-1">Reset</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </spring:form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                <!-- // Basic Horizontal form layout section end -->
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
