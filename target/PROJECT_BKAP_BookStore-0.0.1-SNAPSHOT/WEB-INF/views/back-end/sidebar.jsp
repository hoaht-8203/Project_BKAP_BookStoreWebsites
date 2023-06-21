<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ADMIN PAGE</title>
</head>
<body>
    <div id="sidebar" class="active">
        <div class="sidebar-wrapper active">
            <div class="sidebar-header">
                <div class="d-flex justify-content-between">
                    <div class="logo">
                        <h4>Admin page (${pageContext.request.userPrincipal.name})</h4>
                    </div>
                    <div class="toggler">
                        <a href="#" class="sidebar-hide d-xl-none d-block"><i class="bi bi-x bi-middle"></i></a>
                    </div>
                </div>
            </div>
            <div class="sidebar-menu">
                <ul class="menu">
                    <li class="sidebar-item ${accountManagementActive} has-sub">
                        <a href="loadAllAcc" class='sidebar-link'>
                            <span class="fa fa-user-cog"></span>
                            <span>Account Management</span>
                        </a>
                        <ul class="submenu ${accountManagementActive}">
                            <li class="submenu-item ${loadAllAccActive}">
                                <a href="loadAllAcc">All Account</a>
                            </li>
                            <li class="submenu-item ${loadAllAdminActive}">
                                <a href="loadAllAdmin">Admin Account</a>
                            </li>
                            <li class="submenu-item ${addNewAdminAccActive}">
                                <a href="initAddNewAdminAcc">Add Admin Account</a>
                            </li>
                        </ul>
                    </li>

                    <li class="sidebar-item ${bookManagementActive} has-sub">
                        <a href="" class='sidebar-link'>
                            <span class="fa fa-book"></span>
                            <span>Book Management</span>
                        </a>
                        <ul class="submenu ${bookManagementActive}">
                            <li class="submenu-item ${listAllBookActive}">
                                <a href="loadAllBooks">List All Books</a>
                            </li>
                            <li class="submenu-item ${listAllAuthorActive}">
                                <a href="loadAllAuthor">List All Authors</a>
                            </li>
                            <li class="submenu-item ${listAllGenreActive}">
                                <a href="initAddNewGenre">List All Genres</a>
                            </li>
                            <li class="submenu-item ${listAllFormatActive}">
                                <a href="initAddNewFormat">List All Formats</a>
                            </li>
                            <li class="submenu-item ${listAllSalesActive}">
                                <a href="initAddNewSales">List All Sales</a>
                            </li>
                        </ul>
                    </li>

                    <li class="sidebar-item ${ordersActive} has-sub">
                        <a href="" class='sidebar-link'>
                            <span class="fa fa-clipboard-list"></span>
                            <span>Order Management</span>
                        </a>
                        <ul class="submenu ${ordersActive}">
                            <li class="submenu-item ${listOrdersActive}">
                                <a href="loadAllOrders">List Orders</a>
                            </li>
                            <li class="submenu-item ${ordersChartActive}">
                                <a href="loadOrdersChart">Orders Chart</a>
                            </li>
                        </ul>
                    </li>

                    <li class="sidebar-item ${revenueStatisticsActive}">
                        <a href="loadRevenueStatistic" class='sidebar-link'>
                            <i class="fa fa-poll"></i>
                            <span>Revenue Statistics</span>
                        </a>
                    </li>

                    <li class="sidebar-item ${bookInStockActive}">
                        <a href="loadBookInStock" class='sidebar-link'>
                            <i class="fa fa-dolly-flatbed"></i>
                            <span>Book In Stock</span>
                        </a>
                    </li>

                    <li class="sidebar-item ${loadMyAccountActive}">
                        <a href="loadMyAccount" class='sidebar-link'>
                            <i class="fa fa-user"></i>
                            <span>My Account</span>
                        </a>
                    </li>

                    <li class="sidebar-item">
                        <form id="logoutForm" action="<c:url value="/j_spring_security_logout" />" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <a id="submitLogout" class="sidebar-link"><i class="fa fa-sign-out-alt"></i><span>Logout</span></a>
                        </form>
                    </li>
                </ul>
            </div>
            <button class="sidebar-toggler btn x"><i data-feather="x"></i></button>
        </div>
    </div>

    <script>
        document.getElementById("submitLogout").onclick = function() {
            document.getElementById("logoutForm").submit();
        }
    </script>
</body>
</html>
