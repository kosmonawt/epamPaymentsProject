<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <jsp:include page="fragments/header.jsp"/>
</head>
<body>
<div class="container">
    <jsp:include page="fragments/navbar.jsp"/>

    <form action="<%=request.getContextPath()%>/app/user/accounts/topUp" method="post">
        <div class="form-content">
            <div class="form-group">
                <input type="hidden" value="${requestScope.accNum}" class="form-control" id="accNum" name="accNum">
            </div>
            <br>
            <div class="form-group">
                <label for="amount"><fmt:message key="settings.jsp.form.localization.page.payment.enterAmount"/></label>
                <input type="number" min="0" step="0.01" class="form-control" id="amount" name="amount">
                <c:if test="${not empty requestScope.validateAmount}">
                    <small id="amountError" class="form-text text-muted">${requestScope.validateAmount}"</small>
                </c:if>
            </div>
            <br>
            <button type="submit" class="btn btn-primary"><fmt:message
                    key="settings.jsp.form.localization.button.payment.sendMoney"/></button>
        </div>
    </form>
</div>
</body>
</html>
