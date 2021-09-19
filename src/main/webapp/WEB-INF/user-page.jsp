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
        <label for="userInfo">User Information</label>
        <table class="table table-striped" id="userInfo">
            <tbody>
            <tr>
                <th scope="row">
                    Name
                </th>
                <td>${sessionScope.user.name}</td>
            </tr>
            <tr>
                <th scope="row">
                    Surname
                </th>
                <td>${sessionScope.user.surname}</td>
            </tr>

            <tr>
                <th scope="row">
                    Email
                </th>

                <td>
                    ${sessionScope.user.email}
                </td>
            </tr>

            <tr>
                <th scope="row">
                    Accounts
                </th>
                <td>
                    <a href="<%=request.getContextPath()%>/app/user/accounts"><fmt:message
                            key="settings.jsp.label.localization.accounts"/> </a>
                </td>
            </tr>

            </tbody>
        </table>
    </div>

</div>

</body>
</html>
