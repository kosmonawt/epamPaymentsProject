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

    <br>

    <div class="container">
        <div class="table-responsive">
            <table class="table table-hover table-sm">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="settings.jsp.table.localization.cardNum"/></th>
                    <th scope="col"><fmt:message key="settings.jsp.table.localization.cardType"/></th>
                    <th scope="col"><fmt:message key="settings.jsp.table.localization.cardExpiryDate"/></th>
                    <th scope="col"><fmt:message key="settings.jsp.table.localization.accNum"/></th>

                </tr>
                </thead>

                <tbody>
                <c:forEach items="${requestScope.cards}" var="card">
                    <tr>
                        <th scope="row">${card.cardNumber}</th>
                        <td>${card.cardType}</td>
                        <td>${card.expiryDate}</td>
                        <td>${card.accountNum}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <br>
    <form action="${pageContext.request.contextPath}/app/user/accounts/card" method="post">
        <div class="container">
            <input type="hidden" value="${requestScope.accNumber}" name="accNumber">
        </div>
        <br>
        <button type="submit" class="btn btn-primary">
            <fmt:message key="settings.jsp.table.localization.cardCreateButton"/>
        </button>
    </form>

</div>

</body>
</html>
