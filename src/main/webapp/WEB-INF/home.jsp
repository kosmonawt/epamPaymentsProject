<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@taglib uri="http://example.com/functions" prefix="f" %><%@ taglib prefix="f" uri=""%>--%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <jsp:include page="fragments/header.jsp"/>
</head>
<body>
<div class="container">
    <jsp:include page="fragments/navbar.jsp"/>


    <c:if test="${sessionScope.user.status.equalsIgnoreCase('BLOCKED')}">
        <form action="${pageContext.request.contextPath}/app" method="post">
            <div class="container">
                <input type="hidden" value="${sessionScope.user.email}" name="userEmail">
            </div>
            <br>
            <button type="submit" class="btn btn-outline-primary">
                <fmt:message key="button.unblockRequest"/>
            </button>
        </form>
    </c:if>

    <div class="container py-3">
        <header>
            <div class="pricing-header p-3 pb-md-4 mx-auto text-center">
                <h1 class="display-4 fw-normal">Pricing</h1>
            </div>
        </header>

        <main>
            <div class="row row-cols-1 row-cols-md-3 mb-3 text-center">
                <div class="col">
                    <div class="card mb-4 rounded-3 shadow-sm">
                        <div class="card-header py-3">
                            <h4 class="my-0 fw-normal">Free</h4>
                        </div>
                        <div class="card-body">
                            <h1 class="card-title pricing-card-title">$0<small class="text-muted fw-light">/mo</small></h1>
                            <ul class="list-unstyled mt-3 mb-4">

                            </ul>
                            <button type="button" class="w-100 btn btn-lg btn-outline-primary">Sign up for free</button>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card mb-4 rounded-3 shadow-sm">
                        <div class="card-header py-3">
                            <h4 class="my-0 fw-normal">Pro</h4>
                        </div>
                        <div class="card-body">
                            <h1 class="card-title pricing-card-title">$15<small class="text-muted fw-light">/mo</small></h1>
                            <ul class="list-unstyled mt-3 mb-4">

                            </ul>
                            <button type="button" class="w-100 btn btn-lg btn-primary">Get started</button>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card mb-4 rounded-3 shadow-sm border-primary">
                        <div class="card-header py-3 text-white bg-primary border-primary">
                            <h4 class="my-0 fw-normal">Enterprise</h4>
                        </div>
                        <div class="card-body">
                            <h1 class="card-title pricing-card-title">$29<small class="text-muted fw-light">/mo</small></h1>
                            <ul class="list-unstyled mt-3 mb-4">

                            </ul>
                            <button type="button" class="w-100 btn btn-lg btn-primary">Contact us</button>
                        </div>
                    </div>
                </div>
            </div>

            <h2 class="display-6 text-center mb-4">Compare plans</h2>

            <div class="table-responsive">
                <table class="table text-center">
                    <thead>
                    <tr>
                        <th style="width: 34%;"></th>
                        <th style="width: 22%;">Free</th>
                        <th style="width: 22%;">Pro</th>
                        <th style="width: 22%;">Enterprise</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th scope="row" class="text-start">Public</th>
                        <td><svg class="bi" width="24" height="24"><use xlink:href="#check"></use></svg></td>
                        <td><svg class="bi" width="24" height="24"><use xlink:href="#check"></use></svg></td>
                        <td><svg class="bi" width="24" height="24"><use xlink:href="#check"></use></svg></td>
                    </tr>
                    <tr>
                        <th scope="row" class="text-start">Private</th>
                        <td></td>
                        <td><svg class="bi" width="24" height="24"><use xlink:href="#check"></use></svg></td>
                        <td><svg class="bi" width="24" height="24"><use xlink:href="#check"></use></svg></td>
                    </tr>
                    </tbody>

                    <tbody>
                    <tr>
                        <th scope="row" class="text-start">Permissions</th>
                        <td><svg class="bi" width="24" height="24"><use xlink:href="#check"></use></svg></td>
                        <td><svg class="bi" width="24" height="24"><use xlink:href="#check"></use></svg></td>
                        <td><svg class="bi" width="24" height="24"><use xlink:href="#check"></use></svg></td>
                    </tr>
                    <tr>
                        <th scope="row" class="text-start">Sharing</th>
                        <td></td>
                        <td><svg class="bi" width="24" height="24"><use xlink:href="#check"></use></svg></td>
                        <td><svg class="bi" width="24" height="24"><use xlink:href="#check"></use></svg></td>
                    </tr>
                    <tr>
                        <th scope="row" class="text-start">Unlimited members</th>
                        <td></td>
                        <td><svg class="bi" width="24" height="24"><use xlink:href="#check"></use></svg></td>
                        <td><svg class="bi" width="24" height="24"><use xlink:href="#check"></use></svg></td>
                    </tr>
                    <tr>
                        <th scope="row" class="text-start">Extra security</th>
                        <td></td>
                        <td></td>
                        <td><svg class="bi" width="24" height="24"><use xlink:href="#check"></use></svg></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </main>


    </div>

</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
