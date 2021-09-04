<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <jsp:include page="fragments/header.jsp"/>
</head>
<body>

<fmt:setBundle basename="locale_resources" var="lang"/>

<div class="container">
    <jsp:include page="fragments/navbar.jsp"/>

    <form action="<%=request.getContextPath()%>/register" method="post">
        <div class="form-content">

            <div class="form-group">
                <label for="name"><fmt:message key="settings.jsp.label.localization.name"/></label>
                <input type="text" class="form-control" id="name" name="name"
                       placeholder="<fmt:message key="settings.jsp.label.localization.name.enter"/>">
            </div>
            <div class="form-group">
                <label for="surname"><fmt:message key="settings.jsp.label.localization.surname"/></label>
                <input type="text" class="form-control" id="surname" name="surname"
                       placeholder="<fmt:message key="settings.jsp.label.localization.surname.enter"/>">
            </div>
            <br>
            <div class="form-group">
                <label for="email"><fmt:message key="settings.jsp.label.localization.email"/></label>
                <input type="email" class="form-control" id="email" name="email"
                       placeholder="<fmt:message key="settings.jsp.label.localization.email.enter"/>">
                <c:if test="${not empty emailValidationError}">
                    <small id="emailError" class="form-text text-muted">${emailValidationError}"</small>
                </c:if>
            </div>
            <div class="form-group">
                <label for="password"><fmt:message key="settings.jsp.label.localization.password"/></label>
                <input type="password" class="form-control" id="password" name="password"
                       placeholder="<fmt:message key="settings.jsp.label.localization.password.enter"/>">
            </div>
            <br>
            <button type="submit" class="btn btn-primary">Submit</button>
        </div>
    </form>
</div>


</body>
</html>
