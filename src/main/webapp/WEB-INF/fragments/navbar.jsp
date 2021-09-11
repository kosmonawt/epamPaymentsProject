<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<script>
    function langChange() {
        location.reload();
    }
</script>

<nav class="navbar sticky-top navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/"><fmt:message
                key="settings.jsp.label.localization.homepage"/></a>
        <div class="nav-item">
            <c:if test="${sessionScope.user == null}">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/login"><fmt:message
                        key="settings.jsp.label.localization.login"/> </a>
            </c:if>
            <c:if test="${sessionScope.user == null}">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/register"><fmt:message
                        key="settings.jsp.label.localization.register"/></a>
            </c:if>
            <c:if test="${sessionScope.user != null}">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/logout"><fmt:message
                        key="settings.jsp.label.localization.logout"/></a>
            </c:if>


        </div>
        <div class="nav-item">
            <ul class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown"
                   aria-expanded="false">
                    <fmt:message key="settings.jsp.label.localization.language"/> </a>
                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <li><a class="dropdown-item" href="?lang=UK">UA</a></li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>
                    <li><a class="dropdown-item" href="?lang=EN">EN</a></li>

                </ul>
            </ul>
        </div>

    </div>
</nav>