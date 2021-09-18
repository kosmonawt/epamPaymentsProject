<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@taglib uri="http://example.com/functions" prefix="f" %><%@ taglib prefix="f" uri=""%>--%>
<html>
<head>
    <jsp:include page="fragments/header.jsp"/>
</head>
<body>
<div class="container">
    <jsp:include page="fragments/navbar.jsp"/>

    <div class="align-content-md-center">


        <h5>
            ${sessionScope.user.email}
        </h5>
        <br>
        <h5>
            ${sessionScope.user.id}
        </h5>
        <br>
        <h5>
            ${sessionScope.user.role}
        </h5>

    </div>
    <br>
    <form action="${pageContext.request.contextPath}/app/user/accounts" method="post">
        <div class="container">
            <label for="currency"><fmt:message key="settings.jsp.label.localization.choseCurrency"/> </label> <br>
            <select type="text" name="currency" id="currency">
                <c:forEach var="curr" items="${requestScope.currencies}">
                    <option value="${curr.name()}" >${curr}</option>
                </c:forEach>
            </select>
        </div>
        <br>
        <button type="submit" class="btn btn-primary">
            <fmt:message key="settings.jsp.label.localization.createAccountButton"/>
        </button>
    </form>

    <div class="container">

        <div class="align-content-center">

            <c:forEach items="${requestScope.accounts}" var="account">

                <h4>${account.currency}</h4> <br>
                <h4>${account.amount}</h4> <br>
                <h4>${account.status}</h4> <br>
                <h4>${account.cardId}</h4> <br>
            </c:forEach>

        </div>

    </div>

</div>

</body>
</html>
