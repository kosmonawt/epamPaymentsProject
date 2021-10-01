<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@taglib uri="http://example.com/functions" prefix="f" %><%@ taglib prefix="f" uri=""%>--%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/fragments/navbar.jsp"/>

    <div class="align-content-md-center">

        <p>

            <b>The status code is:</b> <%= request.getAttribute("javax.servlet.error.status_code") %><br>

            <b>The error message again is:</b> <%= request.getAttribute("javax.servlet.error.message") %><br>

        </p>

        <p>
            <b>The exception class is:</b> <%= request.getAttribute("javax.servlet.error.exception") %><br>
        </p>

    </div>

</div>

</body>
</html>
