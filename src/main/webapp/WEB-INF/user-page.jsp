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

        <label for="paymentInfo"><fmt:message key="settings.jsp.table.localization.payment.history"/> </label>
        <br>
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
                        <c:if test="${!payment.paymentStatus.equalsIgnoreCase('SEND')}">
                            <form action="${pageContext.request.contextPath}/app/user/payment" method="post">
                                <input type="hidden" value="${payment.paymentNum}" name="sendPayment">
                                <button type="submit" class="btn btn-primary">
                                    <fmt:message
                                            key="settings.jsp.table.localization.payment.send"/>
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

<footer>


</footer>
</body>
</html>
