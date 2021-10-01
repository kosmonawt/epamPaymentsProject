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

    <form action="<%=request.getContextPath()%>/register" method="post">
        <div class="form-content">

            <div class="form-group">
                <label for="name"><fmt:message key="settings.jsp.label.localization.name"/></label>
                <input type="text" maxlength="160" class="form-control" id="name" name="name"
                       placeholder="<fmt:message key="settings.jsp.label.localization.name.enter"/>" required>
            </div>
            <div class="form-group">
                <label for="surname"><fmt:message key="settings.jsp.label.localization.surname"/></label>
                <input type="text" maxlength="160" class="form-control" id="surname" name="surname"
                       placeholder="<fmt:message key="settings.jsp.label.localization.surname.enter"/>" required>
            </div>
            <br>
            <div class="form-group">
                <label for="email"><fmt:message key="settings.jsp.label.localization.email"/></label>
                <input type="email" maxlength="150" class="form-control" id="email" name="email"
                       placeholder="<fmt:message key="settings.jsp.label.localization.email.enter"/>" required>
                <c:if test="${not empty requestScope.emailValidationError}">
                    <small id="emailError" class="form-text text-muted">${requestScope.emailValidationError}"</small>
                </c:if>
            </div>
            <div class="form-group">
                <label for="password"><fmt:message key="settings.jsp.label.localization.password"/></label>
                <input type="password" class="form-control" id="password" name="password"
                       placeholder="<fmt:message key="settings.jsp.label.localization.password.enter"/>" required>
            </div>
            <br>
            <button type="submit" class="btn btn-primary"><fmt:message
                    key="settings.jsp.label.localization.button.register"/></button>
        </div>
    </form>

    <jsp:include page="fragments/footer.jsp"/>

</div>
</body>
</html>
