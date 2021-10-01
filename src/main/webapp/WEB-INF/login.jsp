<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <jsp:include page="fragments/header.jsp"/>
</head>

<body>
<div class="container">
    <jsp:include page="fragments/navbar.jsp"/>

    <div class="login-form">
        <form action="<%=request.getContextPath()%>/login" method="post">
            <div class="form-content">

                <div class="form-group">
                    <label for="email"><fmt:message key="settings.jsp.label.localization.email"/></label>
                    <input type="email" minlength="1" maxlength="160" class="form-control" id="email" name="email"
                           placeholder="<fmt:message key="settings.jsp.label.localization.email.enter"/>" required>
                </div>
                <div class="form-group">
                    <label for="password"><fmt:message key="settings.jsp.label.localization.password"/></label>
                    <input type="password" minlength="1" class="form-control" id="password" name="password"
                           placeholder="<fmt:message key="settings.jsp.label.localization.password.enter"/>" required>
                </div>
                <br>

                <button type="submit" class="btn btn-primary"><fmt:message
                        key="settings.jsp.label.localization.button.login"/></button>
            </div>
        </form>
    </div>

    <jsp:include page="fragments/footer.jsp"/>

</div>
</body>

</html>
