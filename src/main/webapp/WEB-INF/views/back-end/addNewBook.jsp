<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ADD NEW BOOK</title>
    <%@ include file="import_css.jsp"%>

    <link href="<c:url value="https://unpkg.com/filepond/dist/filepond.css" />" rel="stylesheet">
    <link href="<c:url value="https://unpkg.com/filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css" />" rel="stylesheet">
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
                        <h3>Add New Book</h3>
                    </div>
                    <div class="col-12 col-md-6 order-md-2 order-first">
                        <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                            <ol class="breadcrumb">
                                <li class="breadcrumb-item"><a href="loadAllBooks">Book Management</a></li>
                                <li class="breadcrumb-item"><a href="loadAllBooks">List All Books</a></li>
                                <li class="breadcrumb-item active" aria-current="page">Add New Book</li>
                            </ol>
                        </nav>
                    </div>
                </div>
            </div>
            <!-- Basic card section start -->
            <section id="content-types">
                <div class="row">
                    <div class="col-xl-9 col-md-6 col-sm-12">
                        <section class="section">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Please Enter The Information</h4>
                                </div>
                                <div class="card-content">
                                    <div class="card-body">
                                        <spring:form action="addNewBook" cssClass="form form-horizontal" method="post" modelAttribute="books" enctype="multipart/form-data">
                                            <div class="form-body">
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <label>Book Title</label>
                                                    </div>
                                                    <div class="col-md-9 form-group">
                                                        <spring:input  path="title" cssClass="form-control mb-2"/>
                                                        <spring:errors path="title" cssClass="alert alert-danger p-1 text-center"/>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label>Book Author</label>
                                                    </div>
                                                    <div class="col-md-9 form-group">
                                                        <spring:select cssClass="choices form-select mb-2" path="authors.id">
                                                            <spring:options items="${listAuthor}" itemValue="id" itemLabel="fullName"/>
                                                        </spring:select>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label>Book Genres</label>
                                                    </div>
                                                    <div class="col-md-9 form-group">
                                                        <select class="choices form-select multiple-remove" name="selectGenres" multiple="multiple">
                                                            <c:forEach items="${listGenres}" var="g">
                                                                <option value="${g.id}">${g.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <c:if test="${errorGenre}">
                                                            <span class="alert alert-danger p-1 text-center">Genre is cannot empty!</span>
                                                        </c:if>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label>Sales</label>
                                                    </div>
                                                    <div class="col-md-9 form-group">
                                                        <spring:select cssClass="form-select" path="sales.id">
                                                            <spring:option value="0" label="Not Sales"/>
                                                            <spring:options items="${saleList}" itemValue="id" itemLabel="percentage"/>
                                                        </spring:select>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label>Description Title</label>
                                                    </div>
                                                    <div class="col-md-9 form-group">
                                                        <spring:textarea path="description_title" cssClass="form-control mb-2" cols="85" rows="4"/>
                                                        <spring:errors path="description_title" cssClass="alert alert-danger p-1 text-center"/>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label>Description Detail</label>
                                                    </div>
                                                    <div class="col-md-9 form-group">
                                                        <spring:textarea path="description_detail" cssClass="form-control mb-2" cols="85" rows="7"/>
                                                        <spring:errors path="description_detail" cssClass="alert alert-danger p-1 text-center"/>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label>Book Image</label>
                                                    </div>
                                                    <div class="col-md-9 form-group">
                                                        <input name="bookImg" class="form-control" required="required" type="file">
                                                    </div>
                                                    <div class="col-sm-12 d-flex justify-content-start">
                                                        <button type="submit" class="btn btn-primary me-1 mb-1">Add</button>
                                                        <a href="loadAllBooks" class="btn btn-primary me-1 mb-1">Cancel</a>
                                                        <button type="reset" class="btn btn-light-secondary me-1 mb-1">Reset</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </spring:form>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>
            </section>
            <!-- Basic Card types section end -->
        </div>
    </div>

        <jsp:include page="import_js.jsp"/>
        <script src="<c:url value="https://unpkg.com/filepond-plugin-image-preview/dist/filepond-plugin-image-preview.js"/>"></script>
        <script src="<c:url value="https://unpkg.com/filepond/dist/filepond.js"/>"></script>
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

            FilePond.registerPlugin(
                FilePondPluginImagePreview,
            );

            // Filepond: Image Preview
            FilePond.create( document.querySelector('.image-preview-filepond'), {
                allowImagePreview: true,
                allowImageFilter: false,
                allowImageExifOrientation: false,
                allowImageCrop: false,
                acceptedFileTypes: ['image/png','image/jpg','image/jpeg'],
                fileValidateTypeDetectType: (source, type) => new Promise((resolve, reject) => {
                    // Do custom type detection here and return with promise
                    resolve(type);
                })
            });
        </script>
</body>
</html>
