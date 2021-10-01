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

    <form action="<%=request.getContextPath()%>/app/user/payment" method="post">
        <div class="form-content">
            <div class="form-group">
                <label for="account"><fmt:message key="settings.jsp.form.localization.page.payment.choseAcc"/></label>
                <select  name="accNumber" id="account">
                    <c:forEach var="acc" items="${requestScope.accounts}">
                        <option value="${acc.accountNumber}">${acc.accountNumber}, ${acc.currency}
                            : ${acc.amount}, [${acc.status}]
                        </option>
                    </c:forEach>
                </select>
            </div>
            <br>
            <div class="form-group">
                <label for="accNumTo"><fmt:message
                        key="settings.jsp.form.localization.page.payment.enterAccNumberForPay"/></label>
                <input type="number" min="0" class="form-control" id="accNumTo" name="accNumTo">
            </div>
            <br>
            <div class="form-group">
                <label for="amount"><fmt:message key="settings.jsp.form.localization.page.payment.enterAmount"/></label>
                <input type="number" min="0" step="0.01" class="form-control" id="amount" name="amount">
                <c:if test="${not empty requestScope.validateAmount}">
                    <small id="amountError" class="form-text text-muted">${requestScope.validateAmount}"</small>
                </c:if>
            </div>
            <div class="form-group">
                <label for="email"><fmt:message
                        key="settings.jsp.form.localization.page.payment.enterRecipientEmail"/></label>
                <input type="email" class="form-control" id="email" name="email">
                <c:if test="${not empty requestScope.emailValidationError}">
                    <small id="emailError" class="form-text text-muted">${requestScope.emailValidationError}"</small>
                </c:if>
            </div>
            <br>
            <button type="submit" class="btn btn-primary"><fmt:message
                    key="settings.jsp.form.localization.button.payment.sendMoney"/></button>
        </div>
    </form>

    <jsp:include page="fragments/footer.jsp"/>

</div>

</body>
</html>
