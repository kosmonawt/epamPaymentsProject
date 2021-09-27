<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@taglib uri="http://example.com/functions" prefix="f" %><%@ taglib prefix="f" uri=""%>--%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>


<html>
<head>
    <jsp:include page="fragments/header.jsp"/>

    <title>Accounts</title>
<body>
<div class="container">
    <jsp:include page="fragments/navbar.jsp"/>

    <br>
    <div class="container">
        <table data-toggle="table"
               data-pagination="true"
               data-page-size="10"
               data-height="800"
               data-search="true"
               id="payments"
               class="table  table-hover table-sm" style="width:100%">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="settings.jsp.table.localization.accNum"/></th>
                <th scope="col"><fmt:message key="settings.jsp.table.localization.currency"/></th>
                <th scope="col"><fmt:message key="settings.jsp.table.localization.amount"/></th>
                <th scope="col"><fmt:message key="settings.jsp.table.localization.status"/></th>
                <th scope="col"><fmt:message key="settings.jsp.table.localization.cards"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${requestScope.accounts}" var="account">
                <tr>
                    <th scope="row">${account.accountNumber}</th>
                    <td>${account.currency}</td>
                    <td>${account.amount}</td>
                    <td>${account.status}</td>
                    <td><a class="btn btn-outline-info" role="button"
                           href="${pageContext.request.contextPath}/app/user/accounts/card?accNumber=${account.accountNumber}"><fmt:message
                            key="settings.jsp.table.localization.cards"/></a>
                    </td>
                    <c:if test="${!account.status.equalsIgnoreCase('BLOCKED')}">
                        <c:if test="${!sessionScope.user.admin}">
                            <td>
                                <a class="btn btn-outline-success" role="button"
                                   href="${pageContext.request.contextPath}/app/user/accounts/topUp?accNumber=${account.accountNumber}"><fmt:message
                                        key="settings.jsp.table.localization.payment.refill"/></a>
                            </td>
                        </c:if>
                    </c:if>
                    <c:if test="${sessionScope.user.admin}">
                        <c:if test="${!account.status.equalsIgnoreCase('BLOCKED')}">
                            <td>
                                <form action="${pageContext.request.contextPath}/app/user/accounts" method="post">
                                    <input type="hidden" value="${account.accountNumber}" name="blockAccountNumber">
                                    <button type="submit" class="btn btn-outline-danger">
                                        <fmt:message
                                                key="settings.jsp.table.localization.payment.block"/>
                                    </button>
                                </form>
                            </td>
                        </c:if>
                    </c:if>
                    <c:if test="${account.status.equalsIgnoreCase('BLOCKED')}">
                        <td>
                            <form action="${pageContext.request.contextPath}/app/user/accounts" method="post">
                                <input type="hidden" value="${account.accountNumber}" name="unblockAccountNumber">
                                <button type="submit" class="btn btn-outline-dark">
                                    <fmt:message
                                            key="settings.jsp.table.localization.payment.unblock"/>
                                </button>
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div>
        <br>
        <form action="${pageContext.request.contextPath}/app/user/accounts" method="post">
            <div class="container">
                <label for="currency"><fmt:message key="settings.jsp.label.localization.choseCurrency"/> </label>
                <select type="text" name="currency" id="currency">
                    <c:forEach var="curr" items="${requestScope.currencies}">
                        <option value="${curr.name()}">${curr}</option>
                    </c:forEach>
                </select>
            </div>
            <br>
            <button type="submit" class="btn btn-primary">
                <fmt:message key="settings.jsp.label.localization.createAccountButton"/>
            </button>
        </form>

    </div>

</div>


<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
