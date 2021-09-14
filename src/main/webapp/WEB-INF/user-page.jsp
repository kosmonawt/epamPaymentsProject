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

</div>

</body>
</html>
