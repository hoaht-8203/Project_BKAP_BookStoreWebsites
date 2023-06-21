<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ADD NEW BOOK DETAIL</title>
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
                        <h3>${detailsBookById.title}</h3>
                    </div>
                    <div class="col-12 col-md-6 order-md-2 order-first">
                        <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                            <ol class="breadcrumb">
                                <li class="breadcrumb-item"><a href="loadAllBooks">Book Management</a></li>
                                <li class="breadcrumb-item"><a href="loadAllBooks">List All Books</a></li>
                                <li class="breadcrumb-item"><a href="detailBook?id=${detailsBookById.id}">Detail</a></li>
                                <li class="breadcrumb-item active" aria-current="page">Add New Format To Book</li>
                            </ol>
                        </nav>
                    </div>
                </div>
            </div>
            <!-- Basic card section start -->
            <section id="content-types">
                <div class="row">
                    <div class="col-xl-2 col-md-6 col-sm-12">
                        <div class="card">
                            <div class="card-content">
                                <img src="<c:url value="/resources/images/${detailsBookById.image}"/>" style="border-radius: 0.5rem;" class="img-fluid" alt="This is a book image">
                            </div>
                        </div>
                    </div>

                    <div class="col-xl-10 col-md-6 col-sm-12">
                        <section class="section">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Please Enter The Information</h4>
                                </div>
                                <div class="card-content">
                                    <div class="card-body">
                                        <form:form action="addNewBookDetail" cssClass="form form-horizontal" method="post" modelAttribute="bookDetails">
                                            <div class="form-body">
                                                <div class="row">
                                                    <div class="col-md-2">
                                                        <label>Book ID</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <input type="number" class="form-control" name="bookId" value="${detailsBookById.id}" readonly>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Book Title</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <input type="text" class="form-control" value="${detailsBookById.title}" readonly>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Format Name</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <form:select cssClass="form-select mb-2" path="formats.id">
                                                            <form:options items="${listFormat}" itemValue="id" itemLabel="name"/>
                                                        </form:select>
                                                        <c:if test="${errorFormat}">
                                                            <span class="alert alert-danger p-1 text-center">This format is already be used!</span>
                                                        </c:if>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Publication Date</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <form:input path="publication_date" cssClass="form-control mb-2" type="date"/>
                                                        <form:errors cssClass="alert alert-danger p-1 text-center" path="publication_date"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Publisher</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <form:input path="publishers" cssClass="form-control mb-2"/>
                                                        <form:errors cssClass="alert alert-danger p-1 text-center" path="publishers"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Language</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <form:input path="language" cssClass="form-control mb-2"/>
                                                        <form:errors cssClass="alert alert-danger p-1 text-center" path="language"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Page Number</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <form:input path="page_number" type="number" cssClass="form-control mb-2"/>
                                                        <form:errors cssClass="alert alert-danger p-1 text-center" path="page_number"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Length</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <form:input path="length" type="number" step="any" cssClass="form-control mb-2"/>
                                                        <form:errors cssClass="alert alert-danger p-1 text-center" path="length"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Width</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <form:input path="width" type="number" step="any" cssClass="form-control mb-2"/>
                                                        <form:errors cssClass="alert alert-danger p-1 text-center" path="width"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Thickness</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <form:input path="thickness" type="number" step="any" cssClass="form-control mb-2"/>
                                                        <form:errors cssClass="alert alert-danger p-1 text-center" path="thickness"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Weight</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <form:input path="weight" type="number" step="any" cssClass="form-control mb-2"/>
                                                        <form:errors cssClass="alert alert-danger p-1 text-center" path="weight"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Price</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <form:input path="price" type="number" step="any" cssClass="form-control mb-2"/>
                                                        <form:errors cssClass="alert alert-danger p-1 text-center" path="price"/>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Total Stock</label>
                                                    </div>
                                                    <div class="col-md-4 form-group">
                                                        <form:input path="quantityStock" type="number" cssClass="form-control mb-2"/>
                                                        <form:errors cssClass="alert alert-danger mt-2 p-1 text-center" path="quantityStock"/>
                                                    </div>
                                                    <div class="col-sm-12 d-flex justify-content-start mt-3">
                                                        <button type="submit" class="btn btn-primary me-1 mb-1">Add</button>
                                                        <a class="btn btn-primary me-1 mb-1" href="">Cancel</a>
                                                        <button type="reset" class="btn btn-light-secondary me-1 mb-1">Reset</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </form:form>
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

        document.getElementById('showMyAlert').addEventListener('click', () => {
            Toastify({
                text: "You need to choose format before update",
                duration: 3000,
                close:true,
                gravity:"top",
                position: "center",
                backgroundColor: "#dc3545",
            }).showToast();
        })
    </script>
</body>
</html>
