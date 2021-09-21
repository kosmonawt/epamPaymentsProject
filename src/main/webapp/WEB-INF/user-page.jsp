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


    <div class="align-content-md-center">
        <label for="userInfo"><fmt:message key="settings.jsp.table.localization.information.userInformation"/></label>
        <br>
        <table class="table table-striped" id="userInfo">
            <tbody>
            <tr>
                <th scope="row">
                    <fmt:message key="settings.jsp.table.localization.information.user.name"/>
                </th>
                <td>${sessionScope.user.name}</td>
            </tr>
            <tr>
                <th scope="row">
                    <fmt:message key="settings.jsp.table.localization.information.user.surname"/>
                </th>
                <td>${sessionScope.user.surname}</td>
            </tr>

            <tr>
                <th scope="row">
                    <fmt:message key="settings.jsp.table.localization.information.user.email"/>
                </th>

                <td>
                    ${sessionScope.user.email}
                </td>
            </tr>

            <tr>
                <th scope="row">
                    <fmt:message key="settings.jsp.table.localization.information.user.accounts"/>
                </th>
                <td>
                    <a class="btn btn-outline-primary"
                       href="<%=request.getContextPath()%>/app/user/accounts"><fmt:message
                            key="settings.jsp.label.localization.accounts"/> </a>
                </td>
            </tr>

            </tbody>
        </table>
    </div>
    <br>
    <div class="container">
        <a class="btn btn-outline-primary"
           href="<%=request.getContextPath()%>/app/user/payment"><fmt:message
                key="settings.jsp.table.localization.showPaymentPage"/> </a>
        <br>
        <c:if test="${requestScope.payments != null} ">
            <table class="table table-striped" id="paymentInfo">
                <tbody>
                <tr>
                    <th scope="row">

                    </th>
                    <td>${requestScope.payments}</td>
                </tr>
                <tr>
                    <th scope="row">
                        Surname
                    </th>
                    <td>${sessionScope.user.surname}</td>
                </tr>

                <tr>
                    <th scope="row">
                        Email
                    </th>

                    <td>
                            ${sessionScope.user.email}
                    </td>
                </tr>

                <tr>
                    <th scope="row">
                        Accounts
                    </th>
                    <td>
                        <a class="btn btn-outline-primary"
                           href="<%=request.getContextPath()%>/app/user/accounts"><fmt:message
                                key="settings.jsp.label.localization.accounts"/> </a>
                    </td>
                </tr>

                </tbody>
            </table>
        </c:if>

    </div>

</div>

</body>
</html>
