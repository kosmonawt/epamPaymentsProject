<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page pageEncoding="UTF-8" %>
<%@ taglib prefix="date" uri="dateNow" %>


<footer class="footer border-top" >
    <div class="container">
        <p class="col-md-4 mb-0 text-muted"><fmt:message key="server.time"/></p>
        <p class="col-md-4 mb-0 text-muted"><date:today/></p>
    </div>
</footer>
