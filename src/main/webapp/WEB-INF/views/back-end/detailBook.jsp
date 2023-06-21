<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: hoaht
  Date: 5/5/2023
  Time: 1:07 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BOOK DETAIL</title>
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
                            <h3>${b.title}</h3>
                        </div>
                        <div class="col-12 col-md-6 order-md-2 order-first">
                            <nav aria-label="breadcrumb" class="breadcrumb-header float-start float-lg-end">
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item"><a href="loadAllBooks">Book Management</a></li>
                                    <li class="breadcrumb-item"><a href="loadAllBooks">List All Books</a></li>
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
                                    <img src="<c:url value="/resources/images/${b.image}"/>" class="img-fluid" alt="This is a book image" style="border-radius: 0.5rem;">
                                </div>
                            </div>
                        </div>

                        <div class="col-xl-10 col-md-6 col-sm-12">
                            <div class="card">
                                <div class="card-content">
                                    <div class="card-header">
                                        <div style="display: flex; justify-content: start;">
                                            <a id="showMyAlert" class="btn btn-primary rounded-pill" style="margin-right: 3px;">Update</a>
                                            <div class="dropdown" style="margin-right: 3px;">
                                                <button class="btn btn-primary rounded-pill dropdown-togglec" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Add New Format</button>
                                                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton" style="margin: 0px;">
                                                    <a class="dropdown-item" href="initAddNewBookDetail?id=${b.id}">Add new format to this book</a>
                                                    <a class="dropdown-item" href="initAddNewFormat">Add new format to database</a>
                                                </div>
                                            </div>
                                            <a id="showMyAlert2" class="btn btn-danger rounded-pill">Delete</a>
                                        </div>
                                    </div>
                                    <div class="card-body row">
                                        <div class="col-12 col-md-5">
                                            <h4 class="text-decoration-underline">The information of Books</h4>
                                            <div class="card-content">
                                                <div class="card-body">
                                                    <!-- Table with outer spacing -->
                                                    <div class="table-responsive">
                                                        <table class="table table-lg">
                                                            <tr>
                                                                <th>ID</th>
                                                                <td>
                                                                    ${b.id}
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th>Title</th>
                                                                <td>
                                                                    ${b.title}
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th>Author</th>
                                                                <td>
                                                                    ${b.authors.fullName}
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th>Genres</th>
                                                                <td>
                                                                    <c:forEach items="${listGenres}" var="g">
                                                                        <span class="badge bg-primary mb-1">${g.genres.name}</span>
                                                                    </c:forEach>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th>Create date</th>
                                                                <td>
                                                                    <fmt:formatDate value="${b.create_date}" type="date" pattern="yyyy-MM-dd"/>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th>Sale Name</th>
                                                                <td>
                                                                    <span class="badge bg-primary mb-1">${b.sales.name}</span>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-6 col-md-7">
                                            <h4 class="text-decoration-underline">Description</h4>
                                            <b>${b.description_title}</b><span id="dots">...</span>
                                            <span style="display: none;" id="more"> <br>${b.description_detail}</span>
                                            <br><button onclick="showMoreAndLess()" id="myBtn" class="btn icon btn-sm btn-primary p-0">show more</button>
                                        </div>
                                        <div class="col-12 col-md-8">
                                            <h4 class="text-decoration-underline">All Format Of This Book</h4>
                                            <table class="table table-hover" id="table2">
                                                <thead>
                                                    <tr>
                                                        <th>Format Name</th>
                                                        <th>Publication Date</th>
                                                        <th>Price</th>
                                                        <th>Sold</th>
                                                        <th>Status</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${listFormats}" var="f">
                                                        <tr>
                                                            <td><a href="getDetailFormat?id=${f.id}" class="btn btn-primary">${f.formats.name}</a></td>
                                                            <td>
                                                                <fmt:formatDate value="${f.publication_date}" type="date" pattern="yyyy-MM-dd"/>
                                                            </td>
                                                            <td><fmt:formatNumber value="${f.price}" type="currency"/></td>
                                                            <td>${f.quantitySold}</td>
                                                            <td>
                                                                <c:if test="${f.available}">
                                                                    <span class="badge bg-success">Available</span>
                                                                </c:if>
                                                                <c:if test="${!f.available}">
                                                                    <span class="badge bg-danger">Pre Order</span>
                                                                </c:if>
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
                    </div>
                </section>
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

        document.getElementById('showMyAlert').addEventListener('click', () => {
            Toastify({
                text: "You Need To Choose Format Before Update",
                duration: 3000,
                close:true,
                gravity:"top",
                position: "center",
                backgroundColor: "#ffcc00",
            }).showToast();
        })

        document.getElementById('showMyAlert2').addEventListener('click', () => {
            Toastify({
                text: "You Need To Choose Format Before Delete",
                duration: 3000,
                close:true,
                gravity:"top",
                position: "center",
                backgroundColor: "#dc3545",
            }).showToast();
        })

        function showMoreAndLess() {
            var dots = document.getElementById("dots");
            var moreText = document.getElementById("more");
            var btnText = document.getElementById("myBtn");

            if (dots.style.display === "none") {
                dots.style.display = "inline";
                btnText.innerHTML = "show more";
                moreText.style.display = "none";
            } else {
                dots.style.display = "none";
                btnText.innerHTML = "show less";
                moreText.style.display = "inline";
            }
        }
    </script>
</body>
</html>
