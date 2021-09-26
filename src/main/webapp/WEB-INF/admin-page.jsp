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

    <div class="container">
        <br>
        <label for="paymentInfo"><fmt:message key="settings.jsp.label.localization.page.users"/> </label>
        <table
                data-toggle="table"
                data-pagination="true"
                data-page-size="10"
                data-height="800"
                data-search="true"
                class="table table-striped"
                id="usersInfo" style="width:100%">

            <thead>
            <tr>
                <th scope="col"><fmt:message key="settings.jsp.table.localization.information.user.name"/></th>
                <th scope="col"><fmt:message key="settings.jsp.table.localization.information.user.surname"/></th>
                <th scope="col"><fmt:message key="settings.jsp.table.localization.information.user.email"/></th>
                <th scope="col"><fmt:message key="settings.jsp.table.localization.information.user.accounts"/></th>
                <th></th>

            </tr>
            </thead>

            <tbody>
            <c:forEach items="${requestScope.users}" var="user">
                <tr>
                    <th scope="row">${user.name}</th>
                    <td data-sortable="true">${user.surname}</td>
                    <td>${user.email}</td>
                    <td>
                        <a class="btn btn-outline-success" role="button"
                           href="${pageContext.request.contextPath}/app/user/accounts?userAcc=${user.email}"><fmt:message
                                key="settings.jsp.table.localization.information.user.accounts"/></a>
                    </td>
                    <td>
                        <a class="btn btn-outline-danger" role="button"
                           href="${pageContext.request.contextPath}/app/admin?blockUser=${user.email}"><fmt:message
                                key="settings.jsp.table.localization.payment.block"/></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <br>
    <div class="container">
        <br>
        <label for="paymentInfo"><fmt:message key="settings.jsp.table.localization.payment.approve"/> </label>
        <table
                data-toggle="table"
                data-pagination="true"
                data-page-size="10"
                data-height="800"
                data-search="true"
                class="table table-striped"
                id="paymentInfo" style="width:100%">

            <thead>
            <tr>
                <th scope="col"><fmt:message key="settings.jsp.table.localization.payment.number"/></th>
                <th scope="col"><fmt:message key="settings.jsp.table.localization.payment.accountFrom"/></th>
                <th scope="col"><fmt:message key="settings.jsp.table.localization.payment.accountTo"/></th>
                <th scope="col"><fmt:message key="settings.jsp.table.localization.amount"/></th>
                <th scope="col"><fmt:message key="settings.jsp.table.localization.payment.date"/></th>
                <th scope="col"><fmt:message key="settings.jsp.table.localization.status"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>

            <tbody>

            <c:forEach items="${requestScope.payments}" var="payment">
                <tr>
                    <th scope="row">${payment.paymentNum}</th>
                    <td data-sortable="true">${payment.paymentFromAccount}</td>
                    <td>${payment.paymentToAccount}</td>
                    <td>${payment.amount}</td>
                    <td>${payment.dateTime}</td>
                    <td>${payment.paymentStatus}</td>
                    <td>
                        <c:if test="${!payment.paymentStatus.equalsIgnoreCase('BLOCKED') or !payment.paymentStatus.equalsIgnoreCase('ЗАБЛОКОВАНИЙ') }">
                            <form action="${pageContext.request.contextPath}/app/user/payment" method="post">
                                <input type="hidden" value="${payment.paymentNum}" name="sendPayment">
                                <button type="submit" class="btn btn-primary">
                                    <fmt:message
                                            key="settings.jsp.table.localization.payment.button.approve"/>
                                </button>
                            </form>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${!payment.paymentStatus.equalsIgnoreCase('BLOCKED') or !payment.paymentStatus.equalsIgnoreCase('ЗАБЛОКОВАНИЙ') }">
                            <form action="${pageContext.request.contextPath}/app/user/payment" method="post">
                                <input type="hidden" value="${payment.paymentNum}" name="sendPayment">
                                <button type="submit" class="btn btn-primary">
                                    <fmt:message
                                            key="settings.jsp.table.localization.payment.block"/>
                                </button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</div>


</body>
</html>
