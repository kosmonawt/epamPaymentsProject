<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<nav class="navbar sticky-top navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">Home page</a>

        <c:choose>
            <c:when test="${sessionScope}">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/login">Login</a>
            </c:when>
            <c:when test="${sessionScope.session == null}">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/logout">Logout</a>
            </c:when>
        </c:choose>

    </div>
</nav>
